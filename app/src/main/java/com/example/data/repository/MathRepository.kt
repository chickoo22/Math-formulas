package com.example.data.repository

import com.example.data.local.BookmarkDao
import com.example.data.local.BookmarkEntity
import com.example.data.local.CalcHistoryDao
import com.example.data.local.CalcHistoryEntity
import com.example.data.local.ExamNoteDao
import com.example.data.local.ExamNoteEntity
import com.example.data.model.Formula
import com.example.data.model.FormulaCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MathRepository(
    private val examNoteDao: ExamNoteDao,
    private val bookmarkDao: BookmarkDao,
    private val calcHistoryDao: CalcHistoryDao
) {
    val allNotes: Flow<List<ExamNoteEntity>> = examNoteDao.getAllNotes()

    val bookmarkedFormulaIds: Flow<List<String>> = bookmarkDao.getAllBookmarkedIds()

    val calcHistory: Flow<List<CalcHistoryEntity>> = calcHistoryDao.getRecentHistory()

    // Stream of formulas with isBookmarked flag combined reactively
    val formulasWithBookmarks: Flow<List<FormulaWithBookmark>> = bookmarkedFormulaIds.map { bookmarkedIds ->
        val idSet = bookmarkedIds.toSet()
        MathDataProvider.FORMULAS.map { formula ->
            FormulaWithBookmark(
                formula = formula,
                isBookmarked = idSet.contains(formula.id)
            )
        }
    }

    suspend fun toggleBookmark(formulaId: String) {
        if (bookmarkDao.isBookmarked(formulaId)) {
            bookmarkDao.removeBookmark(formulaId)
        } else {
            bookmarkDao.addBookmark(BookmarkEntity(formulaId))
        }
    }

    suspend fun addNote(note: ExamNoteEntity): Long = examNoteDao.insertNote(note)

    suspend fun updateNote(note: ExamNoteEntity) = examNoteDao.updateNote(note)

    suspend fun togglePinNote(id: Long) = examNoteDao.togglePin(id)

    suspend fun deleteNote(id: Long) = examNoteDao.deleteNote(id)

    suspend fun addCalcHistory(expression: String, result: String) {
        calcHistoryDao.insertHistory(CalcHistoryEntity(expression = expression, result = result))
    }

    suspend fun deleteHistoryItem(id: Long) = calcHistoryDao.deleteHistoryItem(id)

    suspend fun clearCalcHistory() = calcHistoryDao.clearAllHistory()

    suspend fun seedInitialNotesIfEmpty() {
        if (examNoteDao.getCount() == 0) {
            val starterNotes = listOf(
                ExamNoteEntity(
                    title = "⚡ Calculus Exam High-Yield Rules",
                    category = "Calculus",
                    content = "1. Remember LIATE rule for Integration by Parts: Log, Inverse trig, Algebraic, Trig, Exponential.\n2. L'Hôpital only applies to 0/0 or ∞/∞, never for 0·∞ or 1^∞ without transforming first!\n3. Chain Rule: Differentiate outside function first, then multiply by derivative of inside.",
                    formulaSnippet = "∫ u dv = uv - ∫ v du",
                    isPinned = true,
                    colorTag = "#38BDF8"
                ),
                ExamNoteEntity(
                    title = "📌 Quadratic Discriminant Shortcuts",
                    category = "Algebra",
                    content = "Δ = b² - 4ac:\n• Δ > 0: 2 real, distinct solutions (parabola crosses x-axis twice)\n• Δ = 0: 1 real repeated root (parabola touches x-axis at vertex)\n• Δ < 0: 2 complex conjugate roots (parabola does not touch x-axis)",
                    formulaSnippet = "x = (-b ± √(b² - 4ac)) / (2a)",
                    isPinned = true,
                    colorTag = "#F59E0B"
                ),
                ExamNoteEntity(
                    title = "📐 Roots & Exponent Fast Mental Math",
                    category = "Squares & Roots",
                    content = "• To square numbers ending in 5: e.g. 75²: multiply 7 by (7+1)=56, attach 25 → 5625!\n• Cube root of negative numbers is always real and negative: ∛(-x) = -∛x.\n• Always rationalize denominators with conjugate: (a+√b)(a-√b) = a² - b.",
                    formulaSnippet = "1 / (√a + √b) = (√a - √b) / (a - b)",
                    isPinned = false,
                    colorTag = "#10B981"
                ),
                ExamNoteEntity(
                    title = "🎯 Trigonometry Unit Circle Mnemonics",
                    category = "Trigonometry",
                    content = "ASTC: 'All Students Take Calculus'\n• Quadrant I: All positive\n• Quadrant II: Sin positive\n• Quadrant III: Tan positive\n• Quadrant IV: Cos positive",
                    formulaSnippet = "sin²θ + cos²θ = 1",
                    isPinned = false,
                    colorTag = "#A855F7"
                )
            )
            for (note in starterNotes) {
                examNoteDao.insertNote(note)
            }
        }
    }
}

data class FormulaWithBookmark(
    val formula: Formula,
    val isBookmarked: Boolean
)
