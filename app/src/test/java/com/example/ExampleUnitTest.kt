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

  @Test
  fun testEducationLevelClassification() {
    val allFormulas = com.example.data.repository.MathDataProvider.FORMULAS
    val class10Formulas = allFormulas.filter { it.educationLevel == com.example.data.model.EducationLevel.CLASS_10 }
    val class12Formulas = allFormulas.filter { it.educationLevel == com.example.data.model.EducationLevel.CLASS_12 }
    val advancedFormulas = allFormulas.filter { it.educationLevel == com.example.data.model.EducationLevel.ADVANCED }

    assertTrue("Expected at least 18 Class 10 formulas, found ${class10Formulas.size}", class10Formulas.size >= 18)
    assertTrue("Expected at least 25 Class 12 formulas, found ${class12Formulas.size}", class12Formulas.size >= 25)
    assertTrue("Expected at least 4 Advanced formulas, found ${advancedFormulas.size}", advancedFormulas.size >= 4)

    // Verify key Class 10 formulas
    assertTrue(class10Formulas.any { it.id == "alg_quadratic" })
    assertTrue(class10Formulas.any { it.id == "roots_laws_exp" })
    assertTrue(class10Formulas.any { it.id == "std10_real_hcf_lcm" })
    assertTrue(class10Formulas.any { it.id == "std10_coord_distance_section" })
    assertTrue(class10Formulas.any { it.id == "std10_circle_sector_segment" })
    assertTrue(class10Formulas.any { it.id == "std10_mensuration_3d" })
    assertTrue(class10Formulas.any { it.id == "std10_stat_empirical" })
    assertTrue(class10Formulas.any { it.id == "std10_linear_consistency" })

    // Verify key Class 12 formulas
    assertTrue(class12Formulas.any { it.id == "std12_def_integral_kings" })
    assertTrue(class12Formulas.any { it.id == "std12_special_integrals_roots" })
    assertTrue(class12Formulas.any { it.id == "std12_3d_line_shortest_distance" })
    assertTrue(class12Formulas.any { it.id == "std12_matrix_adjoint_properties" })
    assertTrue(class12Formulas.any { it.id == "std12_lmvt_rolles" })
    assertTrue(class12Formulas.any { it.id == "std12_diff_eq_homogeneous" })
    assertTrue(class12Formulas.any { it.id == "std12_prob_total_binomial" })
    assertTrue(class12Formulas.any { it.id == "std12_vector_scalar_triple" })

    // Verify key Advanced formulas
    assertTrue(advancedFormulas.any { it.id == "la_eigenvalues" })
    assertTrue(advancedFormulas.any { it.id == "calc_taylor" })
    assertTrue(advancedFormulas.any { it.id == "roots_nested_radicals" })
  }

  @Test
  fun testClass12PhysicsFormulas() {
    val physicsFormulas = com.example.data.repository.PhysicsDataProvider.FORMULAS
    assertTrue("Expected at least 20 Class 12 Physics formulas, found ${physicsFormulas.size}", physicsFormulas.size >= 20)

    // Verify all have Subject.PHYSICS
    assertTrue(physicsFormulas.all { it.subject == com.example.data.model.Subject.PHYSICS })

    // Verify all 5 core modules are represented
    val categories = physicsFormulas.map { it.category }.toSet()
    assertTrue("Electrostatics covered", categories.contains(com.example.data.model.MathCategory.ELECTROSTATICS))
    assertTrue("Current Electricity covered", categories.contains(com.example.data.model.MathCategory.CURRENT_ELECTRICITY))
    assertTrue("Magnetism & EMI covered", categories.contains(com.example.data.model.MathCategory.MAGNETISM_EMI))
    assertTrue("Optics covered", categories.contains(com.example.data.model.MathCategory.OPTICS))
    assertTrue("Modern Physics covered", categories.contains(com.example.data.model.MathCategory.MODERN_PHYSICS))

    // Verify key physics formulas
    assertTrue(physicsFormulas.any { it.id == "phy12_coulomb" })
    assertTrue(physicsFormulas.any { it.id == "phy12_gauss_law" })
    assertTrue(physicsFormulas.any { it.id == "phy12_drift_velocity" })
    assertTrue(physicsFormulas.any { it.id == "phy12_biot_savart" })
    assertTrue(physicsFormulas.any { it.id == "phy12_faraday_lenz" })
    assertTrue(physicsFormulas.any { it.id == "phy12_lens_maker" })
    assertTrue(physicsFormulas.any { it.id == "phy12_photoelectric" })
    assertTrue(physicsFormulas.any { it.id == "phy12_de_broglie" })
  }

  @Test
  fun testUniqueFormulaIds() {
    val allFormulas = com.example.data.repository.MathDataProvider.FORMULAS
    val ids = allFormulas.map { it.id }
    assertEquals("All formula IDs must be unique", ids.size, ids.toSet().size)
  }
}

