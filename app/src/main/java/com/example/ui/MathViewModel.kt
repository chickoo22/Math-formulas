package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.CalcHistoryEntity
import com.example.data.local.ExamNoteEntity
import com.example.data.repository.FormulaWithBookmark
import com.example.data.repository.MathRepository
import com.example.ui.theme.ThemeMode
import com.example.util.NetworkConnectivityObserver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MathViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MathRepository
    private val networkObserver = NetworkConnectivityObserver(application)

    val isOnline: StateFlow<Boolean> = networkObserver.isConnected.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = networkObserver.checkCurrentConnectivity()
    )

    val formulas: StateFlow<List<FormulaWithBookmark>>
    val notes: StateFlow<List<ExamNoteEntity>>
    val calcHistory: StateFlow<List<CalcHistoryEntity>>

    private val _themeMode = MutableStateFlow(ThemeMode.DARK)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = MathRepository(
            examNoteDao = database.examNoteDao(),
            bookmarkDao = database.bookmarkDao(),
            calcHistoryDao = database.calcHistoryDao()
        )

        formulas = repository.formulasWithBookmarks.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        notes = repository.allNotes.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        calcHistory = repository.calcHistory.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.seedInitialNotesIfEmpty()
        }
    }

    fun toggleThemeMode() {
        _themeMode.value = when (_themeMode.value) {
            ThemeMode.DARK -> ThemeMode.LIGHT
            ThemeMode.LIGHT -> ThemeMode.DARK
            ThemeMode.SYSTEM -> ThemeMode.DARK
        }
    }

    fun toggleBookmark(formulaId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(formulaId)
        }
    }

    fun addNote(note: ExamNoteEntity) {
        viewModelScope.launch {
            repository.addNote(note)
        }
    }

    fun updateNote(note: ExamNoteEntity) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun togglePinNote(id: Long) {
        viewModelScope.launch {
            repository.togglePinNote(id)
        }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }

    fun saveCalcHistory(expression: String, result: String) {
        viewModelScope.launch {
            repository.addCalcHistory(expression, result)
        }
    }

    fun clearCalcHistory() {
        viewModelScope.launch {
            repository.clearCalcHistory()
        }
    }
}
