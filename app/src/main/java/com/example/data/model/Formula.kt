package com.example.data.model

enum class MathCategory(val title: String) {
    ALL("All"),
    ROOTS_POWERS("Roots & Powers"),
    ALGEBRA("Algebra"),
    TRIGONOMETRY("Trigonometry"),
    CALCULUS("Calculus"),
    GEOMETRY("Geometry"),
    STATISTICS("Probability & Stats"),
    LINEAR_ALGEBRA("Linear Algebra"),
    COMPLEX_NUMBERS("Complex Numbers")
}

data class Formula(
    val id: String,
    val title: String,
    val formulaDisplay: String,
    val description: String,
    val category: MathCategory,
    val subcategory: String,
    val variables: List<String> = emptyList(),
    val example: String? = null,
    val memoryTip: String? = null,
    val isHighYield: Boolean = true,
    val searchTags: List<String> = emptyList()
)
