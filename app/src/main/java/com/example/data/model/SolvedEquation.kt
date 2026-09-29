package com.example.data.model

data class SolutionStep(
    val stepNumber: Int,
    val explanation: String,
    val mathExpression: String
)

data class SolvedEquation(
    val id: String,
    val title: String,
    val topic: String,
    val problem: String,
    val steps: List<SolutionStep>,
    val finalAnswer: String,
    val keyTakeaway: String
)
