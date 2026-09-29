package com.example.math

import kotlin.math.*

data class SolverResult(
    val title: String,
    val summary: String,
    val steps: List<String>,
    val finalAnswer: String
)

object EquationSolvers {

    fun solveQuadratic(a: Double, b: Double, c: Double): SolverResult {
        if (a == 0.0) {
            if (b == 0.0) {
                return SolverResult(
                    title = "Linear Degeneracy",
                    summary = "Not an equation: $c = 0",
                    steps = listOf("Since a = 0 and b = 0, this is a constant expression without variables."),
                    finalAnswer = if (c == 0.0) "Infinite solutions (Identity)" else "No solution"
                )
            }
            val x = -c / b
            return SolverResult(
                title = "Linear Equation",
                summary = "Since a = 0, this simplifies to bx + c = 0",
                steps = listOf(
                    "Equation: ${b}x + ($c) = 0",
                    "Subtract $c from both sides: ${b}x = ${-c}",
                    "Divide by $b: x = ${-c} / $b = ${ScientificEvaluator.formatResult(x)}"
                ),
                finalAnswer = "x = ${ScientificEvaluator.formatResult(x)}"
            )
        }

        val discriminant = b * b - 4 * a * c
        val vertexX = -b / (2 * a)
        val vertexY = c - (b * b) / (4 * a)

        val steps = mutableListOf<String>()
        steps.add("Standard form: ax² + bx + c = 0 with a = $a, b = $b, c = $c")
        steps.add("Calculate Discriminant: Δ = b² - 4ac")
        steps.add("Δ = ($b)² - 4($a)($c) = ${b * b} - ${4 * a * c} = $discriminant")
        steps.add("Parabola Vertex: ($vertexX, $vertexY)")

        val finalAns: String
        when {
            discriminant > 0 -> {
                val sqrtD = sqrt(discriminant)
                val x1 = (-b + sqrtD) / (2 * a)
                val x2 = (-b - sqrtD) / (2 * a)
                steps.add("Since Δ > 0, there are two distinct real roots.")
                steps.add("x = (-b ± √Δ) / 2a")
                steps.add("x₁ = (-($b) + √$discriminant) / (2 · $a) = (${-b} + ${ScientificEvaluator.formatResult(sqrtD)}) / ${2 * a} = ${ScientificEvaluator.formatResult(x1)}")
                steps.add("x₂ = (-($b) - √$discriminant) / (2 · $a) = (${-b} - ${ScientificEvaluator.formatResult(sqrtD)}) / ${2 * a} = ${ScientificEvaluator.formatResult(x2)}")
                finalAns = "x₁ = ${ScientificEvaluator.formatResult(x1)},  x₂ = ${ScientificEvaluator.formatResult(x2)}"
            }
            discriminant == 0.0 -> {
                val x = -b / (2 * a)
                steps.add("Since Δ = 0, there is exactly one repeated real root.")
                steps.add("x = -b / 2a = -($b) / (2 · $a) = ${ScientificEvaluator.formatResult(x)}")
                finalAns = "x = ${ScientificEvaluator.formatResult(x)} (double root)"
            }
            else -> {
                val realPart = -b / (2 * a)
                val imaginaryPart = sqrt(-discriminant) / (2 * abs(a))
                steps.add("Since Δ < 0, there are two complex conjugate roots.")
                steps.add("x = (-b ± i√|Δ|) / 2a")
                val realStr = ScientificEvaluator.formatResult(realPart)
                val imagStr = ScientificEvaluator.formatResult(imaginaryPart)
                steps.add("x = $realStr ± ${imagStr}i")
                finalAns = "x = $realStr ± ${imagStr}i"
            }
        }

        return SolverResult(
            title = "Quadratic Solver: ${a}x² + ${b}x + ${c} = 0",
            summary = "Discriminant Δ = $discriminant",
            steps = steps,
            finalAnswer = finalAns
        )
    }

    fun solveLinear2x2(a1: Double, b1: Double, c1: Double, a2: Double, b2: Double, c2: Double): SolverResult {
        val steps = mutableListOf<String>()
        steps.add("System of equations:")
        steps.add("1) ${a1}x + ${b1}y = $c1")
        steps.add("2) ${a2}x + ${b2}y = $c2")

        val d = a1 * b2 - a2 * b1
        val dx = c1 * b2 - c2 * b1
        val dy = a1 * c2 - a2 * c1

        steps.add("Using Cramer's Rule:")
        steps.add("Main Determinant D = (a₁b₂ - a₂b₁) = ($a1 · $b2) - ($a2 · $b1) = $d")
        steps.add("X Determinant Dx = (c₁b₂ - c₂b₁) = ($c1 · $b2) - ($c2 · $b1) = $dx")
        steps.add("Y Determinant Dy = (a₁c₂ - a₂c₁) = ($a1 · $c2) - ($a2 · $c1) = $dy")

        val finalAns: String
        if (abs(d) < 1e-12) {
            if (abs(dx) < 1e-12 && abs(dy) < 1e-12) {
                steps.add("Since D = 0 and Dx = Dy = 0, the lines are coincident.")
                finalAns = "Infinitely many solutions"
            } else {
                steps.add("Since D = 0 but Dx or Dy ≠ 0, the lines are parallel and never intersect.")
                finalAns = "No solution (Inconsistent system)"
            }
        } else {
            val x = dx / d
            val y = dy / d
            steps.add("x = Dx / D = $dx / $d = ${ScientificEvaluator.formatResult(x)}")
            steps.add("y = Dy / D = $dy / $d = ${ScientificEvaluator.formatResult(y)}")
            finalAns = "x = ${ScientificEvaluator.formatResult(x)},  y = ${ScientificEvaluator.formatResult(y)}"
        }

        return SolverResult(
            title = "2x2 Linear System Solver",
            summary = "Determinant D = $d",
            steps = steps,
            finalAnswer = finalAns
        )
    }

    fun simplifyRadical(number: Long, rootDegree: Int = 2): SolverResult {
        if (number <= 0) {
            return SolverResult(
                title = "Radical Simplifier",
                summary = "Radical must be a positive integer",
                steps = listOf("Root simplification is defined for positive integers."),
                finalAnswer = "Input must be > 0"
            )
        }

        val steps = mutableListOf<String>()
        val symbol = if (rootDegree == 3) "∛" else "√"
        steps.add("Objective: Simplify $symbol$number to the form a$symbol b")

        // Prime factorization
        var rem = number
        val factors = mutableMapOf<Long, Int>()
        var d = 2L
        while (d * d <= rem) {
            while (rem % d == 0L) {
                factors[d] = (factors[d] ?: 0) + 1
                rem /= d
            }
            d += if (d == 2L) 1L else 2L
        }
        if (rem > 1) {
            factors[rem] = (factors[rem] ?: 0) + 1
        }

        val factorString = factors.entries.joinToString(" × ") { (p, count) ->
            if (count == 1) "$p" else "$p^$count"
        }
        steps.add("Prime Factorization of $number = $factorString")

        var outside = 1L
        var inside = 1L

        factors.forEach { (prime, count) ->
            val outPower = count / rootDegree
            val inPower = count % rootDegree
            for (i in 0 until outPower) outside *= prime
            for (i in 0 until inPower) inside *= prime
        }

        val decimalVal = if (rootDegree == 3) number.toDouble().pow(1.0 / 3.0) else sqrt(number.toDouble())
        val formattedDec = String.format("%.4f", decimalVal)

        val finalAns = when {
            inside == 1L -> "$outside (Exact Integer = $formattedDec)"
            outside == 1L -> "$symbol$inside (Already in simplest form ≈ $formattedDec)"
            else -> "$outside$symbol$inside (≈ $formattedDec)"
        }

        steps.add("Extract groups of $rootDegree:")
        steps.add("Outside coefficient = $outside")
        steps.add("Remaining radicand = $inside")
        steps.add("Decimal approximation = $formattedDec")

        return SolverResult(
            title = "$symbol$number Simplification",
            summary = "Simplified: $finalAns",
            steps = steps,
            finalAnswer = finalAns
        )
    }

    fun solveRightTriangle(a: Double?, b: Double?, c: Double?): SolverResult {
        val steps = mutableListOf<String>()
        steps.add("Right Triangle with legs a, b and hypotenuse c (c² = a² + b²)")

        val solvedA: Double
        val solvedB: Double
        val solvedC: Double

        if (a != null && b != null) {
            solvedA = a
            solvedB = b
            solvedC = sqrt(a * a + b * b)
            steps.add("Given legs: a = $a, b = $b")
            steps.add("Hypotenuse c = √(a² + b²) = √($a² + $b²) = √(${a*a + b*b}) = ${ScientificEvaluator.formatResult(solvedC)}")
        } else if (a != null && c != null) {
            if (c <= a) {
                return SolverResult(
                    title = "Invalid Triangle",
                    summary = "Hypotenuse c must be strictly greater than leg a",
                    steps = listOf("In a right triangle, hypotenuse c > a."),
                    finalAnswer = "Error: c must be > a"
                )
            }
            solvedA = a
            solvedC = c
            solvedB = sqrt(c * c - a * a)
            steps.add("Given leg a = $a and hypotenuse c = $c")
            steps.add("Leg b = √(c² - a²) = √($c² - $a²) = √(${c*c - a*a}) = ${ScientificEvaluator.formatResult(solvedB)}")
        } else if (b != null && c != null) {
            if (c <= b) {
                return SolverResult(
                    title = "Invalid Triangle",
                    summary = "Hypotenuse c must be strictly greater than leg b",
                    steps = listOf("In a right triangle, hypotenuse c > b."),
                    finalAnswer = "Error: c must be > b"
                )
            }
            solvedB = b
            solvedC = c
            solvedA = sqrt(c * c - b * b)
            steps.add("Given leg b = $b and hypotenuse c = $c")
            steps.add("Leg a = √(c² - b²) = √($c² - $b²) = √(${c*c - b*b}) = ${ScientificEvaluator.formatResult(solvedA)}")
        } else {
            return SolverResult(
                title = "Right Triangle Solver",
                summary = "Please provide any 2 sides",
                steps = listOf("Provide either both legs (a, b) or one leg and hypotenuse (a, c or b, c)."),
                finalAnswer = "Need 2 values"
            )
        }

        val angleA = Math.toDegrees(asin(solvedA / solvedC))
        val angleB = 90.0 - angleA
        val area = 0.5 * solvedA * solvedB
        val perimeter = solvedA + solvedB + solvedC

        steps.add("Angle α (opposite to a) = arcsin(a/c) = ${ScientificEvaluator.formatResult(angleA)}°")
        steps.add("Angle β (opposite to b) = 90° - α = ${ScientificEvaluator.formatResult(angleB)}°")
        steps.add("Area = ½ · a · b = ½ · $solvedA · $solvedB = ${ScientificEvaluator.formatResult(area)}")
        steps.add("Perimeter = a + b + c = ${ScientificEvaluator.formatResult(perimeter)}")

        val ans = "a = ${ScientificEvaluator.formatResult(solvedA)}, b = ${ScientificEvaluator.formatResult(solvedB)}, c = ${ScientificEvaluator.formatResult(solvedC)} | α = ${ScientificEvaluator.formatResult(angleA)}°, β = ${ScientificEvaluator.formatResult(angleB)}°"

        return SolverResult(
            title = "Right Triangle Solution",
            summary = "Area = ${ScientificEvaluator.formatResult(area)}, Perimeter = ${ScientificEvaluator.formatResult(perimeter)}",
            steps = steps,
            finalAnswer = ans
        )
    }
}
