package com.example.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.ui.graphics.vector.ImageVector

enum class FormulaCategory(
    val title: String,
    val description: String,
    val icon: ImageVector
) {
    ROOTS_AND_POWERS(
        title = "Squares & Roots",
        description = "Squares, Square Roots, Cubes, Cube Roots, and Exponent Laws",
        icon = Icons.Default.SquareFoot
    ),
    ALGEBRA(
        title = "Algebra",
        description = "Quadratics, Factoring, Binomial, Logarithms, & Progressions",
        icon = Icons.Default.Functions
    ),
    TRIGONOMETRY(
        title = "Trigonometry",
        description = "Ratios, Identities, Double Angles, Laws of Sine & Cosine",
        icon = Icons.Default.Category
    ),
    CALCULUS(
        title = "Calculus",
        description = "Limits, Derivatives, Integrals, and Differential Equations",
        icon = Icons.Default.ShowChart
    ),
    GEOMETRY(
        title = "Geometry & Vectors",
        description = "Coordinate Geometry, Conics, 3D, and Vector Operations",
        icon = Icons.Default.CompareArrows
    ),
    STATISTICS(
        title = "Stats & Probability",
        description = "Distributions, Central Tendency, Permutations, & Bayes' Rule",
        icon = Icons.Default.Percent
    ),
    MATRICES(
        title = "Linear Algebra",
        description = "Determinants, Inverses, Eigenvalues, and Systems of Equations",
        icon = Icons.Default.Memory
    ),
    COMPLEX_NUMBERS(
        title = "Complex Numbers",
        description = "Polar & Euler Forms, De Moivre's Theorem, Roots of Unity",
        icon = Icons.Default.Hub
    )
}
