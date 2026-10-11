package com.example.math

import java.util.Locale
import kotlin.math.*

data class SolverResult(
    val title: String,
    val summary: String,
    val steps: List<String>,
    val finalAnswer: String
)

object EquationSolvers {

    fun solveQuadratic(a: Double, b: Double, c: Double): SolverResult {
        if (abs(a) < 1e-9) {
            // Linear degenerate equation
            if (abs(b) < 1e-9) {
                return SolverResult(
                    title = "Degenerate Equation",
                    summary = "No solution or infinitely many solutions",
                    steps = listOf("a = 0 and b = 0: Not an equation with variable x"),
                    finalAnswer = "Undefined"
                )
            }
            val root = -c / b
            return SolverResult(
                title = "Linear Equation (a = 0)",
                summary = "Reduced to bx + c = 0",
                steps = listOf("Equation is linear: ${formatNumber(b)}x + ${formatNumber(c)} = 0", "x = -c / b = ${formatNumber(-c)} / ${formatNumber(b)}"),
                finalAnswer = "x = ${formatNumber(root)}"
            )
        }

        val discriminant = b * b - 4 * a * c
        val vertexX = -b / (2 * a)
        val vertexY = a * vertexX * vertexX + b * vertexX + c

        val steps = mutableListOf<String>()
        steps.add("Standard form: ax² + bx + c = 0 with a = ${formatNumber(a)}, b = ${formatNumber(b)}, c = ${formatNumber(c)}")
        steps.add("Discriminant Δ = b² - 4ac = (${formatNumber(b)})² - 4(${formatNumber(a)})(${formatNumber(c)}) = ${formatNumber(discriminant)}")
        steps.add("Parabola Vertex: (${formatNumber(vertexX)}, ${formatNumber(vertexY)}) with axis of symmetry x = ${formatNumber(vertexX)}")

        val finalAnswer: String
        if (abs(discriminant) < 1e-9) {
            val root = -b / (2 * a)
            steps.add("Δ = 0: Exactly one repeated real root")
            steps.add("x = -b / (2a) = ${formatNumber(-b)} / (2 · ${formatNumber(a)}) = ${formatNumber(root)}")
            finalAnswer = "x = ${formatNumber(root)} (repeated root)"
        } else if (discriminant > 0) {
            val sqrtDelta = sqrt(discriminant)
            val root1 = (-b + sqrtDelta) / (2 * a)
            val root2 = (-b - sqrtDelta) / (2 * a)
            steps.add("Δ > 0: Two distinct real roots")
            steps.add("√Δ = ${formatNumber(sqrtDelta)}")
            steps.add("x₁ = (-b + √Δ)/(2a) = (${formatNumber(-b)} + ${formatNumber(sqrtDelta)}) / (2 · ${formatNumber(a)}) = ${formatNumber(root1)}")
            steps.add("x₂ = (-b - √Δ)/(2a) = (${formatNumber(-b)} - ${formatNumber(sqrtDelta)}) / (2 · ${formatNumber(a)}) = ${formatNumber(root2)}")
            finalAnswer = "x₁ = ${formatNumber(root1)},  x₂ = ${formatNumber(root2)}"
        } else {
            val sqrtAbsDelta = sqrt(-discriminant)
            val realPart = -b / (2 * a)
            val imagPart = sqrtAbsDelta / (2 * a)
            steps.add("Δ < 0: Complex conjugate root pair")
            steps.add("√(-Δ) · i = ${formatNumber(sqrtAbsDelta)}i")
            steps.add("x = [ -b ± i√(4ac - b²) ] / (2a)")
            finalAnswer = "${formatNumber(realPart)} ± ${formatNumber(abs(imagPart))}i"
        }

        return SolverResult(
            title = "Quadratic Equation Solution",
            summary = "Solved via quadratic formula and discriminant analysis",
            steps = steps,
            finalAnswer = finalAnswer
        )
    }

    fun solveLinear2x2(
        a1: Double, b1: Double, c1: Double,
        a2: Double, b2: Double, c2: Double
    ): SolverResult {
        val d = a1 * b2 - a2 * b1
        val dx = c1 * b2 - c2 * b1
        val dy = a1 * c2 - a2 * c1

        val steps = mutableListOf<String>()
        steps.add("System of linear equations:\n(1) ${formatNumber(a1)}x + ${formatNumber(b1)}y = ${formatNumber(c1)}\n(2) ${formatNumber(a2)}x + ${formatNumber(b2)}y = ${formatNumber(c2)}")
        steps.add("Coefficient determinant D = a₁b₂ - a₂b₁ = (${formatNumber(a1)})(${formatNumber(b2)}) - (${formatNumber(a2)})(${formatNumber(b1)}) = ${formatNumber(d)}")

        if (abs(d) < 1e-9) {
            return if (abs(dx) < 1e-9 && abs(dy) < 1e-9) {
                steps.add("D = 0 and Dx = Dy = 0: Infinitely many solutions (dependent coincident lines)")
                SolverResult("Linear System", "Infinitely many solutions", steps, "Infinite solutions")
            } else {
                steps.add("D = 0 and Dx ≠ 0: No solution (parallel lines)")
                SolverResult("Linear System", "Inconsistent system", steps, "No solution")
            }
        }

        steps.add("Dx = c₁b₂ - c₂b₁ = (${formatNumber(c1)})(${formatNumber(b2)}) - (${formatNumber(c2)})(${formatNumber(b1)}) = ${formatNumber(dx)}")
        steps.add("Dy = a₁c₂ - a₂c₁ = (${formatNumber(a1)})(${formatNumber(c2)}) - (${formatNumber(a2)})(${formatNumber(c1)}) = ${formatNumber(dy)}")

        val x = dx / d
        val y = dy / d
        steps.add("x = Dx / D = ${formatNumber(dx)} / ${formatNumber(d)} = ${formatNumber(x)}")
        steps.add("y = Dy / D = ${formatNumber(dy)} / ${formatNumber(d)} = ${formatNumber(y)}")

        return SolverResult(
            title = "2×2 Linear System (Cramer's Rule)",
            summary = "Unique intersection point found",
            steps = steps,
            finalAnswer = "x = ${formatNumber(x)},  y = ${formatNumber(y)}"
        )
    }

    fun solveRightTriangle(a: Double?, b: Double?, c: Double?): SolverResult {
        val steps = mutableListOf<String>()
        steps.add("Right triangle Pythagorean theorem: a² + b² = c²")

        if (a != null && b != null && a > 0 && b > 0) {
            val hyp = sqrt(a * a + b * b)
            val angleA = Math.toDegrees(atan(a / b))
            val angleB = 90.0 - angleA
            steps.add("Given legs a = ${formatNumber(a)} and b = ${formatNumber(b)}")
            steps.add("Hypotenuse c = √(a² + b²) = √(${formatNumber(a * a)} + ${formatNumber(b * b)}) = √${formatNumber(a * a + b * b)} = ${formatNumber(hyp)}")
            steps.add("Angle α = arctan(a/b) = ${formatNumber(angleA)}°")
            steps.add("Angle β = 90° - α = ${formatNumber(angleB)}°")
            steps.add("Area = 0.5 · a · b = 0.5 · ${formatNumber(a)} · ${formatNumber(b)} = ${formatNumber(0.5 * a * b)}")
            return SolverResult("Right Triangle Solution", "Calculated hypotenuse and angles", steps, "c = ${formatNumber(hyp)}")
        } else if (a != null && c != null && a > 0 && c > 0) {
            if (c <= a) {
                return SolverResult("Invalid Triangle", "Hypotenuse c must be strictly greater than leg a", listOf("c ≤ a violates right triangle properties"), "Error: c must be > a")
            }
            val legB = sqrt(c * c - a * a)
            val angleA = Math.toDegrees(asin(a / c))
            val angleB = 90.0 - angleA
            steps.add("Given leg a = ${formatNumber(a)} and hypotenuse c = ${formatNumber(c)}")
            steps.add("Leg b = √(c² - a²) = √(${formatNumber(c * c)} - ${formatNumber(a * a)}) = √${formatNumber(c * c - a * a)} = ${formatNumber(legB)}")
            steps.add("Angle α = arcsin(a/c) = ${formatNumber(angleA)}°")
            steps.add("Angle β = 90° - α = ${formatNumber(angleB)}°")
            steps.add("Area = 0.5 · a · b = ${formatNumber(0.5 * a * legB)}")
            return SolverResult("Right Triangle Solution", "Calculated missing leg b", steps, "b = ${formatNumber(legB)}")
        } else if (b != null && c != null && b > 0 && c > 0) {
            if (c <= b) {
                return SolverResult("Invalid Triangle", "Hypotenuse c must be strictly greater than leg b", listOf("c ≤ b violates right triangle properties"), "Error: c must be > b")
            }
            val legA = sqrt(c * c - b * b)
            val angleB = Math.toDegrees(asin(b / c))
            val angleA = 90.0 - angleB
            steps.add("Given leg b = ${formatNumber(b)} and hypotenuse c = ${formatNumber(c)}")
            steps.add("Leg a = √(c² - b²) = √(${formatNumber(c * c)} - ${formatNumber(b * b)}) = √${formatNumber(c * c - b * b)} = ${formatNumber(legA)}")
            steps.add("Angle α = 90° - β = ${formatNumber(angleA)}°")
            steps.add("Area = 0.5 · a · b = ${formatNumber(0.5 * legA * b)}")
            return SolverResult("Right Triangle Solution", "Calculated missing leg a", steps, "a = ${formatNumber(legA)}")
        }

        return SolverResult("Right Triangle", "Enter at least two values (either two legs or one leg and hypotenuse)", listOf("Requires 2 positive parameters"), "Awaiting inputs")
    }

    fun simplifyRadical(number: Long, rootDegree: Int = 2): SolverResult {
        if (number <= 0) {
            return SolverResult("Radical Simplifier", "Please provide a positive integer", listOf("Requires positive number"), "Undefined")
        }
        if (number == 1L) {
            return SolverResult("Radical Simplifier", "1 to any power is 1", listOf("ⁿ√1 = 1"), "1")
        }

        var temp = number
        var outside = 1L
        var inside = 1L

        // Prime factorization
        val factors = mutableMapOf<Long, Int>()
        var d = 2L
        while (d * d <= temp) {
            var count = 0
            while (temp % d == 0L) {
                count++
                temp /= d
            }
            if (count > 0) factors[d] = count
            d += if (d == 2L) 1 else 2
        }
        if (temp > 1) {
            factors[temp] = 1
        }

        val steps = mutableListOf<String>()
        val rootSymbol = if (rootDegree == 3) "∛" else "√"
        steps.add("Target: $rootSymbol$number")

        val factorStrings = factors.map { (p, exp) -> if (exp > 1) "$p^$exp" else "$p" }
        steps.add("Prime factorization of $number: ${factorStrings.joinToString(" · ")}")

        factors.forEach { (prime, count) ->
            val outPower = count / rootDegree
            val remPower = count % rootDegree
            for (i in 0 until outPower) outside *= prime
            for (i in 0 until remPower) inside *= prime
        }

        val answer = when {
            inside == 1L -> "$outside (exact perfect ${if (rootDegree == 3) "cube" else "square"})"
            outside == 1L -> "$rootSymbol$inside (cannot be simplified further)"
            else -> "$outside$rootSymbol$inside"
        }

        steps.add("Extracted outside radical: $outside (from powers divisible by $rootDegree)")
        steps.add("Remaining inside radical: $inside")
        steps.add("Decimal approximation: ≈ ${formatNumber(number.toDouble().pow(1.0 / rootDegree))}")

        return SolverResult(
            title = "Radical Simplification",
            summary = "Decomposed into canonical simplified radical form",
            steps = steps,
            finalAnswer = answer
        )
    }

    private fun formatNumber(value: Double): String {
        return if (value % 1.0 == 0.0) {
            String.format(Locale.US, "%.0f", value)
        } else {
            String.format(Locale.US, "%.4f", value).trimEnd('0').trimEnd('.')
        }
    }
}
