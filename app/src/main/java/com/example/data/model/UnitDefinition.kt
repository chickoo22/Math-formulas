package com.example.data.model

data class UnitDefinition(
    val name: String,
    val symbol: String,
    val toBaseFactor: Double = 1.0,
    val offset: Double = 0.0
)
