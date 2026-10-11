package com.example

import com.example.data.model.UnitCategory
import com.example.data.repository.UnitConverterData
import com.example.math.EquationSolvers
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testQuadraticSolver_realRoots() {
    val result = EquationSolvers.solveQuadratic(1.0, -5.0, 6.0)
    assertTrue(result.finalAnswer.contains("3") && result.finalAnswer.contains("2"))
  }

  @Test
  fun testQuadraticSolver_complexRoots() {
    val result = EquationSolvers.solveQuadratic(1.0, 2.0, 5.0)
    assertTrue(result.finalAnswer.contains("i"))
  }

  @Test
  fun testLinearSystemSolver() {
    val result = EquationSolvers.solveLinear2x2(3.0, 2.0, 12.0, 1.0, -1.0, 1.0)
    assertTrue(result.finalAnswer.contains("2.8") && result.finalAnswer.contains("1.8"))
  }

  @Test
  fun testRadicalSimplifier() {
    val result = EquationSolvers.simplifyRadical(72, 2)
    assertEquals("6√2", result.finalAnswer)
  }

  @Test
  fun testUnitConverter_angle() {
    val deg = UnitConverterData.CATEGORY_UNITS[UnitCategory.ANGLE]!!.first { it.name == "Degrees" }
    val rad = UnitConverterData.CATEGORY_UNITS[UnitCategory.ANGLE]!!.first { it.name == "Radians" }
    val converted = UnitConverterData.convert(180.0, deg, rad, UnitCategory.ANGLE)
    assertEquals(Math.PI, converted, 1e-4)
  }
}

