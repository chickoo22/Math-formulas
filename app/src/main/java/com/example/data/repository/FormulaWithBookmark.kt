package com.example.data.repository

import com.example.data.model.Formula

data class FormulaWithBookmark(
    val formula: Formula,
    val isBookmarked: Boolean = false
)
