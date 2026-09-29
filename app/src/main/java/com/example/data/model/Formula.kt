package com.example.data.model

data class Formula(
    val id: String,
    val title: String,
    val category: FormulaCategory,
    val subcategory: String,
    val formulaDisplay: String, // Clean math unicode/LaTeX-style representation
    val description: String,
    val variables: List<String> = emptyList(),
    val notes: String? = null,
    val example: String? = null,
    val tags: List<String> = emptyList()
)
