package com.example.data.model

data class NumberPowerRoot(
    val n: Int,
    val square: Long,
    val squareRoot: Double,
    val cube: Long,
    val cubeRoot: Double
) {
    val squareRootFormatted: String
        get() = String.format("%.4f", squareRoot)

    val cubeRootFormatted: String
        get() = String.format("%.4f", cubeRoot)
}
