package com.example

import com.example.data.model.UnitCategory
import com.example.data.model.UnitConverterData
import com.example.math.EquationSolvers
import com.example.math.ScientificEvaluator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MathEvaluatorTest {

    @Test
    fun testScientificEvaluatorBasic() {
        val evaluator = ScientificEvaluator(isDegreeMode = true)
        val res1 = evaluator.evaluate("2 + 3 * 4")
        assertTrue(res1.isSuccess)
        assertEquals(14.0, res1.getOrThrow(), 1e-6)

        val res2 = evaluator.evaluate("sqrt(16) + 3^2")
        assertTrue(res2.isSuccess)
        assertEquals(13.0, res2.getOrThrow(), 1e-6)

        val res3 = evaluator.evaluate("cbrt(27)")
        assertTrue(res3.isSuccess)
        assertEquals(3.0, res3.getOrThrow(), 1e-6)
    }

    @Test
    fun testTrigonometryEvaluator() {
        val evaluator = ScientificEvaluator(isDegreeMode = true)
        val resSin = evaluator.evaluate("sin(30)")
        assertTrue(resSin.isSuccess)
        assertEquals(0.5, resSin.getOrThrow(), 1e-4)

        val resCos = evaluator.evaluate("cos(60)")
        assertTrue(resCos.isSuccess)
        assertEquals(0.5, resCos.getOrThrow(), 1e-4)
    }

    @Test
    fun testQuadraticSolver() {
        val result = EquationSolvers.solveQuadratic(1.0, -5.0, 6.0)
        assertTrue(result.finalAnswer.contains("3"))
        assertTrue(result.finalAnswer.contains("2"))
    }

    @Test
    fun testRadicalSimplifier() {
        val res72 = EquationSolvers.simplifyRadical(72, 2)
        assertTrue(res72.finalAnswer.contains("6√2"))

        val resCbrt = EquationSolvers.simplifyRadical(108, 3)
        assertTrue(resCbrt.finalAnswer.contains("3∛4"))
    }

    @Test
    fun testUnitConverter() {
        val units = UnitConverterData.getUnitsForCategory(UnitCategory.ANGLE)
        val deg = units.first { it.id == "deg" }
        val rad = units.first { it.id == "rad" }
        val converted = UnitConverterData.convert("180", deg, rad, UnitCategory.ANGLE)
        assertEquals("3.141593", converted)
    }
}
