package com.example.data.model

enum class Subject(val title: String, val iconBadge: String) {
    MATH("Mathematics", "📐"),
    PHYSICS("Class 12 Physics", "⚡")
}

enum class MathCategory(val title: String) {
    ALL("All"),
    // Mathematics
    ROOTS_POWERS("Roots & Powers"),
    ALGEBRA("Algebra"),
    TRIGONOMETRY("Trigonometry"),
    CALCULUS("Calculus"),
    GEOMETRY("Geometry"),
    STATISTICS("Probability & Stats"),
    LINEAR_ALGEBRA("Linear Algebra"),
    COMPLEX_NUMBERS("Complex Numbers"),

    // Class 12 Physics
    ELECTROSTATICS("Electrostatics & Capacitance"),
    CURRENT_ELECTRICITY("Current Electricity"),
    MAGNETISM_EMI("Magnetism & EMI"),
    OPTICS("Optics (Ray & Wave)"),
    MODERN_PHYSICS("Modern Physics & Devices")
}

enum class EducationLevel(val label: String, val shortBadge: String) {
    ALL("All Grades", "ALL"),
    CLASS_10("Class 10th", "STD 10"),
    CLASS_12("Class 12th", "STD 12"),
    ADVANCED("College / Advanced", "ADV")
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
    val searchTags: List<String> = emptyList(),
    val educationLevel: EducationLevel = EducationLevel.CLASS_12,
    val subject: Subject = Subject.MATH
)
