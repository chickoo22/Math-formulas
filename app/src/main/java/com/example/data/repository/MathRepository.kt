package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.Formula
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MathRepository(
    private val examNoteDao: ExamNoteDao,
    private val bookmarkDao: BookmarkDao,
    private val calcHistoryDao: CalcHistoryDao
) {
    val formulasWithBookmarks: Flow<List<FormulaWithBookmark>> =
        bookmarkDao.getAllBookmarkedIds().map { bookmarkedIds ->
            val set = bookmarkedIds.toSet()
            MathDataProvider.FORMULAS.map { formula ->
                FormulaWithBookmark(
                    formula = formula,
                    isBookmarked = set.contains(formula.id)
                )
            }
        }

    val allNotes: Flow<List<ExamNoteEntity>> = examNoteDao.getAllNotes()

    val calcHistory: Flow<List<CalcHistoryEntity>> = calcHistoryDao.getRecentHistory()

    suspend fun toggleBookmark(formulaId: String) {
        if (bookmarkDao.isBookmarked(formulaId)) {
            bookmarkDao.deleteBookmark(formulaId)
        } else {
            bookmarkDao.insertBookmark(BookmarkEntity(formulaId))
        }
    }

    suspend fun addNote(note: ExamNoteEntity): Long = examNoteDao.insertNote(note)

    suspend fun updateNote(note: ExamNoteEntity) = examNoteDao.updateNote(note)

    suspend fun togglePinNote(id: Long) = examNoteDao.togglePin(id)

    suspend fun deleteNote(id: Long) = examNoteDao.deleteById(id)

    suspend fun addCalcHistory(expression: String, result: String) =
        calcHistoryDao.insertHistory(CalcHistoryEntity(expression = expression, result = result))

    suspend fun clearCalcHistory() = calcHistoryDao.clearAll()

    suspend fun seedInitialNotesIfEmpty() {
        if (examNoteDao.getNoteCount() == 0) {
            val initialNotes = listOf(
                ExamNoteEntity(
                    title = "Calculus Integration by Parts Shortcut (LIATE)",
                    content = "Prioritize u selection in order:\n1. Logarithmic: ln x, log x\n2. Inverse trig: arctan x, arcsin x\n3. Algebraic: x², 3x, polynomials\n4. Trigonometric: sin x, cos x\n5. Exponential: eˣ, 2ˣ\n\nWhatever is earliest in LIATE becomes u. The rest is dv.",
                    category = "Calculus",
                    isPinned = true
                ),
                ExamNoteEntity(
                    title = "Quadratic Discriminant Rapid Check",
                    content = "Δ = b² - 4ac:\n• Δ > 0 and perfect square: Two rational roots (can factor by grouping)\n• Δ > 0 but not square: Two irrational roots (use quadratic formula)\n• Δ = 0: Exactly one repeated rational root -b/(2a)\n• Δ < 0: Complex conjugate pair roots",
                    category = "Algebra",
                    isPinned = true
                ),
                ExamNoteEntity(
                    title = "Trig Exact Values Hexagon Mnemonic",
                    content = "Key identities:\n• tan = sin / cos\n• cot = cos / sin\n• sin²θ + cos²θ = 1\n• sec²θ - tan²θ = 1\n• csc²θ - cot²θ = 1\n\nOpposite vertices are reciprocals: sin·csc=1, cos·sec=1, tan·cot=1.",
                    category = "Trigonometry",
                    isPinned = false
                )
            )
            initialNotes.forEach { examNoteDao.insertNote(it) }
        }
    }
}
