package com.example.data.repository

import com.example.data.model.Formula
import com.example.data.model.FormulaCategory
import com.example.data.model.NumberPowerRoot
import com.example.data.model.SolutionStep
import com.example.data.model.SolvedEquation
import kotlin.math.cbrt
import kotlin.math.sqrt

object MathDataProvider {

    val NUMBERS_1_TO_50: List<NumberPowerRoot> by lazy {
        (1..50).map { n ->
            NumberPowerRoot(
                n = n,
                square = (n.toLong() * n),
                squareRoot = sqrt(n.toDouble()),
                cube = (n.toLong() * n * n),
                cubeRoot = cbrt(n.toDouble())
            )
        }
    }

    val FORMULAS: List<Formula> = listOf(
        // ==========================================
        // 1. SQUARES, SQUARE ROOTS, CUBES & POWERS
        // ==========================================
        Formula(
            id = "roots_exp_laws",
            title = "Laws of Exponents & Powers",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Powers & Exponents",
            formulaDisplay = "aᵐ · aⁿ = aᵐ⁺ⁿ\n(aᵐ)ⁿ = aᵐⁿ\n(ab)ⁿ = aⁿ · bⁿ\na⁻ⁿ = 1 / aⁿ  (a ≠ 0)\na⁰ = 1  (a ≠ 0)\naᵐ / aⁿ = aᵐ⁻ⁿ",
            description = "Fundamental algebraic laws governing integer, rational, and real exponents.",
            variables = listOf("a, b = nonzero real bases", "m, n = real exponents"),
            example = "2³ · 2⁴ = 2³⁺⁴ = 2⁷ = 128",
            tags = listOf("powers", "exponents", "indices", "rules")
        ),
        Formula(
            id = "roots_fractional_exp",
            title = "Fractional Exponents & Radicals",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Radical Rules",
            formulaDisplay = "a¹/ⁿ = ⁿ√a\naᵐ/ⁿ = (ⁿ√a)ᵐ = ⁿ√(aᵐ)\nⁿ√(a · b) = ⁿ√a · ⁿ√b\nⁿ√(a / b) = ⁿ√a / ⁿ√b",
            description = "Exact equivalences between fractional exponents and nth root radicals.",
            variables = listOf("a, b = positive real numbers", "n = index of radical (n ≥ 2)", "m = numerator power"),
            example = "8²/³ = (∛8)² = 2² = 4",
            tags = listOf("fractional", "roots", "radicals", "rational exponent")
        ),
        Formula(
            id = "roots_sqrt_product_quotient",
            title = "Square Root Simplification Rules",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Square Roots",
            formulaDisplay = "√(a · b) = √a · √b  (a, b ≥ 0)\n√(a / b) = √a / √b  (a ≥ 0, b > 0)\n(√a)² = a  (a ≥ 0)\n√(a²) = |a|  (for all real a)",
            description = "Product and quotient simplification properties for principal square roots.",
            variables = listOf("a, b = non-negative real numbers"),
            example = "√72 = √(36 · 2) = √36 · √2 = 6√2",
            tags = listOf("square root", "product rule", "quotient", "simplification")
        ),
        Formula(
            id = "roots_cbrt_properties",
            title = "Cube Root Properties & Negative Radicands",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Cube Roots",
            formulaDisplay = "∛(a³) = a\n∛(a · b) = ∛a · ∛b\n∛(-a) = -∛a\n(∛a)³ = a",
            description = "Unlike square roots, cube roots are uniquely defined over all real numbers (both positive and negative).",
            variables = listOf("a, b = any real numbers"),
            example = "∛(-125) = -∛(125) = -5, because (-5)³ = -125",
            tags = listOf("cube root", "properties", "negative radicand")
        ),
        Formula(
            id = "roots_diff_of_squares",
            title = "Difference of Two Squares",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Squares",
            formulaDisplay = "a² - b² = (a - b)(a + b)\na⁴ - b⁴ = (a² - b²)(a² + b²) = (a - b)(a + b)(a² + b²)",
            description = "Standard algebraic identity for factoring difference of square and higher even powers.",
            variables = listOf("a, b = real numbers or algebraic expressions"),
            example = "x² - 49 = (x - 7)(x + 7)",
            tags = listOf("square", "factoring", "conjugate", "difference of squares")
        ),
        Formula(
            id = "roots_sum_squares_cubes_series",
            title = "Sum of First n Squares & Cubes",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Series",
            formulaDisplay = "∑ [k=1 to n] k = n(n + 1) / 2\n∑ [k=1 to n] k² = n(n + 1)(2n + 1) / 6\n∑ [k=1 to n] k³ = [n(n + 1) / 2]² = (∑ k)²",
            description = "High-yield formulas for the sum of consecutive integers, squares, and cubes.",
            variables = listOf("n = positive integer limit"),
            example = "Sum of 1² + 2² + 3² + 4² + 5² = 5(6)(11)/6 = 55",
            tags = listOf("sum of squares", "sum of cubes", "series", "integers")
        ),
        Formula(
            id = "roots_cube_sum_diff",
            title = "Sum & Difference of Two Cubes",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Cubes",
            formulaDisplay = "a³ + b³ = (a + b)(a² - ab + b²)\na³ - b³ = (a - b)(a² + ab + b²)",
            description = "Factoring identities for the sum and difference of two cubes.",
            variables = listOf("a, b = real numbers or terms"),
            example = "x³ - 64 = (x - 4)(x² + 4x + 16)",
            tags = listOf("cube", "sum of cubes", "difference of cubes", "factoring")
        ),
        Formula(
            id = "roots_binomial_cube_trinomial",
            title = "Square & Cube of Polynomials",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Expansions",
            formulaDisplay = "(a + b + c)² = a² + b² + c² + 2(ab + bc + ca)\n(a + b)³ = a³ + 3a²b + 3ab² + b³ = a³ + b³ + 3ab(a + b)\n(a - b)³ = a³ - 3a²b + 3ab² - b³ = a³ - b³ - 3ab(a - b)",
            description = "Complete algebraic expansions for square of a trinomial and cube of a binomial.",
            variables = listOf("a, b, c = algebraic terms"),
            example = "(x + y + 2)² = x² + y² + 4 + 2xy + 4y + 4x",
            tags = listOf("trinomial square", "binomial cube", "expansion")
        ),
        Formula(
            id = "roots_denesting_radicals",
            title = "Denesting Nested Radicals",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Radical Rules",
            formulaDisplay = "√(a ± √b) = √((a + c)/2) ± √((a - c)/2)\nwhere c = √(a² - b)  (provided a² - b is a perfect square)",
            description = "Splits a compound radical √(a ± √b) into two simple square roots.",
            variables = listOf("a, b > 0 with a² > b and c = √(a² - b)"),
            example = "√(5 + 2√6) = √(5 + √24): a=5, b=24 → c=√(25-24)=1 → √((5+1)/2) + √((5-1)/2) = √3 + √2",
            tags = listOf("nested radical", "denest", "double root", "simplification")
        ),
        Formula(
            id = "roots_rationalizing",
            title = "Rationalizing the Denominator",
            category = FormulaCategory.ROOTS_AND_POWERS,
            subcategory = "Radical Rules",
            formulaDisplay = "1 / √a = √a / a\n1 / (√a + √b) = (√a - √b) / (a - b)\n1 / (√a - √b) = (√a + √b) / (a - b)\n1 / (∛a - ∛b) = (∛a² + ∛(ab) + ∛b²) / (a - b)",
            description = "Eliminating radicals from denominators by multiplying by radical conjugates.",
            variables = listOf("a, b > 0 and a ≠ b"),
            example = "1 / (√7 - √3) = (√7 + √3) / (7 - 3) = (√7 + √3) / 4",
            tags = listOf("rationalize", "conjugate", "square root", "denominator")
        ),

        // ==========================================
        // 2. ALGEBRA
        // ==========================================
        Formula(
            id = "alg_quadratic_formula",
            title = "Quadratic Formula & Discriminant Analysis",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Polynomial Equations",
            formulaDisplay = "For ax² + bx + c = 0 (a ≠ 0):\nx = (-b ± √(b² - 4ac)) / (2a)\nDiscriminant Δ = b² - 4ac:\n• Δ > 0: 2 distinct real roots\n• Δ = 0: 1 repeated real root (-b/2a)\n• Δ < 0: 2 complex conjugate roots",
            description = "Finds exact roots and nature of solutions of any quadratic equation.",
            variables = listOf("a = leading coefficient", "b = linear coefficient", "c = constant term"),
            example = "x² - 5x + 6 = 0: Δ = 25 - 24 = 1 → x = (5 ± 1)/2 → x = 3, 2",
            tags = listOf("quadratic", "roots", "discriminant", "parabola")
        ),
        Formula(
            id = "alg_vietas_formulas",
            title = "Vieta's Formulas (Quadratic & Cubic)",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Polynomial Equations",
            formulaDisplay = "Quadratic ax² + bx + c = 0:\n  x₁ + x₂ = -b / a\n  x₁ · x₂ = c / a\n\nCubic ax³ + bx² + cx + d = 0:\n  x₁ + x₂ + x₃ = -b / a\n  x₁x₂ + x₂x₃ + x₃x₁ = c / a\n  x₁ · x₂ · x₃ = -d / a",
            description = "Direct relations between polynomial roots and their coefficients.",
            variables = listOf("x₁, x₂, x₃ = roots of the polynomial"),
            example = "For 2x² - 8x + 6 = 0: Sum = 8/2 = 4, Product = 6/2 = 3",
            tags = listOf("vieta", "sum of roots", "product of roots", "cubic")
        ),
        Formula(
            id = "alg_binomial_theorem",
            title = "Binomial Theorem & General Term",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Expansions",
            formulaDisplay = "(a + b)ⁿ = ∑ [k=0 to n] (ⁿCₖ) aⁿ⁻ᵏ bᵏ\nGeneral Term Tᵣ₊₁ = (ⁿCᵣ) aⁿ⁻ʳ bʳ\nⁿCᵣ = n! / [r!(n - r)!]\nTotal number of terms = n + 1",
            description = "Formula for expanding any non-negative integer power of a binomial sum.",
            variables = listOf("n = positive integer exponent", "r = term index (0 ≤ r ≤ n)"),
            example = "(x + 2)⁴ = x⁴ + 4x³(2) + 6x²(4) + 4x(8) + 16 = x⁴ + 8x³ + 24x² + 32x + 16",
            tags = listOf("binomial theorem", "pascals triangle", "general term")
        ),
        Formula(
            id = "alg_log_identities_extended",
            title = "Logarithm Identities & Change of Base",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Logarithms",
            formulaDisplay = "logᵦ(xy) = logᵦ(x) + logᵦ(y)\nlogᵦ(x / y) = logᵦ(x) - logᵦ(y)\nlogᵦ(xᵏ) = k · logᵦ(x)\nlogᵦ(x) = ln(x) / ln(b) = log₁₀(x) / log₁₀(b)\nlogᵦ(a) = 1 / logₐ(b)\nb^(logᵦ x) = x",
            description = "Standard operational laws of logarithms for all positive bases.",
            variables = listOf("b > 0, b ≠ 1", "x, y > 0", "k = real number"),
            example = "log₂ 32 = log₂ (2⁵) = 5",
            tags = listOf("logarithm", "log rules", "natural log", "change of base")
        ),
        Formula(
            id = "alg_progressions_ap_gp_hp",
            title = "Progressions: AP, GP, & AM-GM-HM Inequality",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Sequences & Series",
            formulaDisplay = "Arithmetic (AP): aₙ = a + (n-1)d,  Sₙ = (n/2)[2a + (n-1)d]\nGeometric (GP): aₙ = arⁿ⁻¹,  Sₙ = a(1-rⁿ)/(1-r),  S∞ = a/(1-r) (|r| < 1)\nInequality of Means (for positive a, b):\nAM = (a + b)/2,  GM = √(ab),  HM = 2ab/(a + b)\nAM ≥ GM ≥ HM  (equality holds iff a = b)",
            description = "Essential series formulas and classic arithmetic-geometric-harmonic mean inequalities.",
            variables = listOf("a = initial term", "d = common difference", "r = common ratio"),
            example = "For numbers 4 and 16: AM = 10, GM = 8, HM = 6.4 → 10 ≥ 8 ≥ 6.4",
            tags = listOf("AP", "GP", "HP", "AM-GM", "inequality", "series")
        ),
        Formula(
            id = "alg_cauchy_schwarz",
            title = "Cauchy-Schwarz Inequality",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Inequalities",
            formulaDisplay = "(∑ [i=1 to n] aᵢ bᵢ)² ≤ (∑ [i=1 to n] aᵢ²) (∑ [i=1 to n] bᵢ²)\nVector Form: |a · b| ≤ ‖a‖ ‖b‖",
            description = "Fundamental mathematical inequality in algebra, analysis, and vector geometry.",
            variables = listOf("aᵢ, bᵢ = real numbers"),
            example = "For (1, 2) and (3, 4): (1·3 + 2·4)² = 11² = 121; (1+4)(9+16) = 5·25 = 125 → 121 ≤ 125",
            tags = listOf("cauchy schwarz", "inequality", "vector dot product")
        ),
        Formula(
            id = "alg_remainder_factor_theorem",
            title = "Remainder & Factor Theorems",
            category = FormulaCategory.ALGEBRA,
            subcategory = "Polynomials",
            formulaDisplay = "Remainder Theorem:\nWhen polynomial P(x) is divided by (x - c), the remainder is R = P(c).\n\nFactor Theorem:\n(x - c) is a factor of P(x) if and only if P(c) = 0.",
            description = "Tests polynomial divisibility and extracts roots without full polynomial long division.",
            variables = listOf("P(x) = polynomial in x", "c = test root constant"),
            example = "P(x) = x³ - 3x + 2. P(1) = 1 - 3 + 2 = 0 → (x - 1) is an exact factor.",
            tags = listOf("remainder theorem", "factor theorem", "polynomial division")
        ),

        // ==========================================
        // 3. TRIGONOMETRY
        // ==========================================
        Formula(
            id = "trig_pythagorean_identities",
            title = "Pythagorean Trigonometric Identities",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Identities",
            formulaDisplay = "sin²θ + cos²θ = 1\n1 + tan²θ = sec²θ\n1 + cot²θ = csc²θ\nsin²θ = 1 - cos²θ\ncos²θ = 1 - sin²θ",
            description = "Fundamental identities derived from the unit circle and the Pythagorean theorem.",
            variables = listOf("θ = angle in radians or degrees"),
            example = "If cos θ = 3/5, then sin θ = √(1 - 9/25) = 4/5 (in Quadrant I)",
            tags = listOf("pythagorean", "trig identities", "unit circle")
        ),
        Formula(
            id = "trig_compound_angle",
            title = "Compound Angles (Sum & Difference)",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Identities",
            formulaDisplay = "sin(A ± B) = sinA cosB ± cosA sinB\ncos(A ± B) = cosA cosB ∓ sinA sinB\ntan(A ± B) = (tanA ± tanB) / (1 ∓ tanA tanB)",
            description = "Evaluates trigonometric functions of compound angle sums and differences.",
            variables = listOf("A, B = arbitrary angles"),
            example = "sin(75°) = sin(45°+30°) = (√2/2)(√3/2) + (√2/2)(1/2) = (√6 + √2)/4",
            tags = listOf("compound angles", "angle sum", "angle difference")
        ),
        Formula(
            id = "trig_double_half_triple",
            title = "Double, Half, & Triple Angle Formulas",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Multiple Angles",
            formulaDisplay = "Double Angle:\nsin(2θ) = 2 sinθ cosθ\ncos(2θ) = cos²θ - sin²θ = 2cos²θ - 1 = 1 - 2sin²θ\ntan(2θ) = 2tanθ / (1 - tan²θ)\n\nHalf Angle:\nsin(θ/2) = ±√((1 - cosθ)/2)\ncos(θ/2) = ±√((1 + cosθ)/2)\ntan(θ/2) = (1 - cosθ) / sinθ = sinθ / (1 + cosθ)\n\nTriple Angle:\nsin(3θ) = 3 sinθ - 4 sin³θ\ncos(3θ) = 4 cos³θ - 3 cosθ",
            description = "Complete set of multiple angle and sub-multiple angle trigonometric transformations.",
            variables = listOf("θ = base angle"),
            example = "cos(60°) = 1 - 2sin²(30°) = 1 - 2(1/4) = 0.5",
            tags = listOf("double angle", "half angle", "triple angle", "sin 2x", "cos 2x")
        ),
        Formula(
            id = "trig_product_to_sum",
            title = "Product-to-Sum & Sum-to-Product",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Transformations",
            formulaDisplay = "Product to Sum:\n2 sinA cosB = sin(A + B) + sin(A - B)\n2 cosA sinB = sin(A + B) - sin(A - B)\n2 cosA cosB = cos(A + B) + cos(A - B)\n2 sinA sinB = cos(A - B) - cos(A + B)\n\nSum to Product:\nsin C + sin D = 2 sin((C+D)/2) cos((C-D)/2)\ncos C + cos D = 2 cos((C+D)/2) cos((C-D)/2)",
            description = "Essential identities for transforming products of sine/cosine into sums, vital for integration.",
            variables = listOf("A, B, C, D = angles"),
            example = "2 sin(45°) cos(15°) = sin(60°) + sin(30°) = √3/2 + 1/2",
            tags = listOf("product to sum", "sum to product", "prosthaphaeresis")
        ),
        Formula(
            id = "trig_laws_triangles",
            title = "Law of Sines, Cosines, & Heron's Formula",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Triangles",
            formulaDisplay = "Law of Sines: a / sinA = b / sinB = c / sinC = 2R\nLaw of Cosines: c² = a² + b² - 2ab cosC\nArea of Triangle: Δ = ½ab sinC\nHeron's Formula: Δ = √(s(s - a)(s - b)(s - c))\nwhere s = (a + b + c) / 2 [semi-perimeter]",
            description = "Solves arbitrary (oblique) triangles given side-angle combinations.",
            variables = listOf("a, b, c = side lengths", "A, B, C = opposite angles", "R = circumradius"),
            example = "Triangle with a=5, b=6, c=7: s = 9 → Δ = √(9 · 4 · 3 · 2) = √216 = 6√6 ≈ 14.70",
            tags = listOf("sine rule", "cosine rule", "heron formula", "triangle area")
        ),
        Formula(
            id = "trig_standard_values",
            title = "Standard Angles Reference Table",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Values",
            formulaDisplay = "0° (0 rad): sin=0, cos=1, tan=0\n30° (π/6): sin=1/2, cos=√3/2, tan=1/√3\n45° (π/4): sin=√2/2, cos=√2/2, tan=1\n60° (π/3): sin=√3/2, cos=1/2, tan=√3\n90° (π/2): sin=1, cos=0, tan=undefined\n180° (π): sin=0, cos=-1, tan=0\n270° (3π/2): sin=-1, cos=0, tan=undefined",
            description = "Exact radical values for trigonometric functions at standard textbook angles.",
            variables = listOf("Degrees and radian equivalents"),
            tags = listOf("standard angles", "exact values", "trig table")
        ),
        Formula(
            id = "trig_hyperbolic_functions",
            title = "Hyperbolic Functions & Fundamental Identity",
            category = FormulaCategory.TRIGONOMETRY,
            subcategory = "Hyperbolic",
            formulaDisplay = "sinh(x) = (eˣ - e⁻ˣ) / 2\ncosh(x) = (eˣ + e⁻ˣ) / 2\ntanh(x) = sinh(x) / cosh(x) = (eˣ - e⁻ˣ) / (eˣ + e⁻ˣ)\ncosh²(x) - sinh²(x) = 1\n1 - tanh²(x) = sech²(x)",
            description = "Definitions and identities of hyperbolic functions analogous to circular trigonometry.",
            variables = listOf("x = real variable"),
            example = "cosh(0) = (1 + 1)/2 = 1,  sinh(0) = (1 - 1)/2 = 0",
            tags = listOf("hyperbolic", "sinh", "cosh", "tanh", "catenary")
        ),

        // ==========================================
        // 4. CALCULUS
        // ==========================================
        Formula(
            id = "calc_derivatives_rules",
            title = "Differentiation Rules (Product, Quotient, Chain)",
            category = FormulaCategory.CALCULUS,
            subcategory = "Derivatives",
            formulaDisplay = "Power Rule: d/dx(xⁿ) = n xⁿ⁻¹\nProduct Rule: (uv)' = u'v + uv'\nQuotient Rule: (u / v)' = (u'v - uv') / v²\nChain Rule: d/dx [f(g(x))] = f'(g(x)) · g'(x)\nImplicit: d/dx [y²] = 2y (dy/dx)",
            description = "Foundational operational rules required to differentiate algebraic and transcendental functions.",
            variables = listOf("u, v = differentiable functions of x", "n = real exponent"),
            example = "d/dx [x³ sin x] = 3x² sin x + x³ cos x",
            tags = listOf("derivatives", "chain rule", "product rule", "quotient rule")
        ),
        Formula(
            id = "calc_common_derivatives",
            title = "Standard Derivatives Table",
            category = FormulaCategory.CALCULUS,
            subcategory = "Derivatives",
            formulaDisplay = "d/dx(sin x) = cos x\nd/dx(cos x) = -sin x\nd/dx(tan x) = sec²x\nd/dx(sec x) = sec x tan x\nd/dx(eˣ) = eˣ,  d/dx(aˣ) = aˣ ln(a)\nd/dx(ln|x|) = 1 / x\nd/dx(arcsin x) = 1 / √(1 - x²)\nd/dx(arctan x) = 1 / (1 + x²)",
            description = "Table of standard derivatives for trigonometric, exponential, log, and inverse trig functions.",
            variables = listOf("x = independent real variable"),
            example = "d/dx(arctan(2x)) = 2 / (1 + 4x²)",
            tags = listOf("derivatives table", "trig derivatives", "exponential", "arcsin")
        ),
        Formula(
            id = "calc_mean_value_theorems",
            title = "Rolle's & Lagrange's Mean Value Theorem",
            category = FormulaCategory.CALCULUS,
            subcategory = "Theorems",
            formulaDisplay = "Rolle's Theorem:\nIf f is continuous on [a, b], diff on (a, b), and f(a) = f(b),\nthen ∃ c ∈ (a, b) such that f'(c) = 0.\n\nLagrange's Mean Value Theorem (MVT):\nIf f is continuous on [a, b] and diff on (a, b),\nthen ∃ c ∈ (a, b) such that:  f'(c) = [f(b) - f(a)] / (b - a)",
            description = "Guarantees existence of a point where the instantaneous rate matches average rate of change.",
            variables = listOf("f = continuous and differentiable function", "c = intermediate point in (a, b)"),
            example = "For f(x) = x² on [0, 2]: f'(c) = (4 - 0)/(2 - 0) = 2 → 2c = 2 → c = 1",
            tags = listOf("MVT", "rolles theorem", "mean value theorem", "derivatives")
        ),
        Formula(
            id = "calc_taylor_series",
            title = "Taylor & Maclaurin Series Expansions",
            category = FormulaCategory.CALCULUS,
            subcategory = "Series",
            formulaDisplay = "Taylor Series at x = a:\nf(x) = ∑ [n=0 to ∞] [fⁿ(a) / n!] (x - a)ⁿ\n\nMaclaurin Series (a = 0):\neˣ = 1 + x + x²/2! + x³/3! + ...\nsin x = x - x³/3! + x⁵/5! - x⁷/7! + ...\ncos x = 1 - x²/2! + x⁴/4! - x⁶/6! + ...\nln(1 + x) = x - x²/2 + x³/3 - x⁴/4 + ...  (|x| < 1)\n1 / (1 - x) = 1 + x + x² + x³ + ...  (|x| < 1)",
            description = "Polynomial series approximations for smooth mathematical functions.",
            variables = listOf("fⁿ(a) = nth derivative evaluated at center point a"),
            example = "First 2 terms of sin(0.1) ≈ 0.1 - (0.001)/6 ≈ 0.099833",
            tags = listOf("taylor series", "maclaurin series", "polynomial approximation")
        ),
        Formula(
            id = "calc_standard_integrals",
            title = "Table of Standard Integrals",
            category = FormulaCategory.CALCULUS,
            subcategory = "Integrals",
            formulaDisplay = "∫ xⁿ dx = (xⁿ⁺¹ / (n + 1)) + C  (n ≠ -1)\n∫ (1 / x) dx = ln|x| + C\n∫ eˣ dx = eˣ + C\n∫ sin x dx = -cos x + C\n∫ cos x dx = sin x + C\n∫ sec²x dx = tan x + C\n∫ 1 / (x² + a²) dx = (1 / a) arctan(x / a) + C\n∫ 1 / √(a² - x²) dx = arcsin(x / a) + C\n∫ 1 / √(x² ± a²) dx = ln|x + √(x² ± a²)| + C",
            description = "Standard indefinite integrals and anti-derivative forms.",
            variables = listOf("C = arbitrary constant of integration", "a = nonzero constant"),
            example = "∫ (3x² + 4x - 5) dx = x³ + 2x² - 5x + C",
            tags = listOf("integrals", "anti-derivatives", "integration table")
        ),
        Formula(
            id = "calc_integration_by_parts",
            title = "Integration by Parts & Substitution",
            category = FormulaCategory.CALCULUS,
            subcategory = "Integrals",
            formulaDisplay = "Integration by Parts:\n∫ u dv = u·v - ∫ v du\nPriority Order for u: LIATE\n(Logarithmic, Inverse Trig, Algebraic, Trigonometric, Exponential)\n\nSubstitution Rule:\n∫ f(g(x)) g'(x) dx = ∫ f(u) du  where u = g(x)",
            description = "Core techniques for integrating products and nested composite functions.",
            variables = listOf("u, v = differentiable functions", "du, dv = differentials"),
            example = "∫ x eˣ dx: u=x, dv=eˣdx → uv - ∫v du = x eˣ - eˣ + C",
            tags = listOf("by parts", "u substitution", "liate rule")
        ),
        Formula(
            id = "calc_arc_length_surface",
            title = "Arc Length & Surface of Revolution",
            category = FormulaCategory.CALCULUS,
            subcategory = "Applications",
            formulaDisplay = "Arc Length of Curve y = f(x):\nL = ∫ [a to b] √(1 + [f'(x)]²) dx\n\nSurface Area of Revolution (about x-axis):\nS = 2π ∫ [a to b] y √(1 + [y']²) dx\n\nVolume of Revolution (Disk Method):\nV = π ∫ [a to b] [f(x)]² dx",
            description = "Formulas for curve length, surface area, and volume of solids of revolution.",
            variables = listOf("f'(x) = dy/dx", "[a, b] = bounds of integration"),
            example = "Circle x²+y²=r² circumference: 4 ∫ [0 to r] √(1 + x²/(r²-x²)) dx = 2πr",
            tags = listOf("arc length", "surface of revolution", "volume of revolution")
        ),
        Formula(
            id = "calc_differential_equations",
            title = "First-Order Linear Differential Equations",
            category = FormulaCategory.CALCULUS,
            subcategory = "Differential Equations",
            formulaDisplay = "Standard Form:  dy/dx + P(x) y = Q(x)\nIntegrating Factor:  I(x) = e^(∫ P(x) dx)\nGeneral Solution:  y · I(x) = ∫ [Q(x) · I(x)] dx + C\n\nSeparable Form:  dy/dx = g(x) h(y) → ∫ (1/h(y)) dy = ∫ g(x) dx",
            description = "Method of integrating factors and separation of variables for first-order ODEs.",
            variables = listOf("P(x), Q(x) = continuous functions of x", "I(x) = integrating factor"),
            example = "dy/dx + y = eˣ: P=1 → I = eˣ → y eˣ = ∫ e²ˣ dx = ½e²ˣ + C → y = ½eˣ + Ce⁻ˣ",
            tags = listOf("differential equations", "integrating factor", "ODE", "separable")
        ),

        // ==========================================
        // 5. GEOMETRY & COORDINATE GEOMETRY
        // ==========================================
        Formula(
            id = "geom_coordinate_2d",
            title = "2D Coordinate Geometry & Straight Lines",
            category = FormulaCategory.GEOMETRY,
            subcategory = "Coordinate Geometry",
            formulaDisplay = "Distance Formula:  d = √((x₂ - x₁)² + (y₂ - y₁)²)\nMidpoint:  M = ((x₁ + x₂)/2,  (y₁ + y₂)/2)\nSection Formula (ratio m:n):  ((mx₂ + nx₁)/(m+n),  (my₂ + ny₁)/(m+n))\nSlope of Line:  m = (y₂ - y₁) / (x₂ - x₁) = tan θ\nPoint-Slope Form:  y - y₁ = m(x - x₁)\nPerpendicular Distance from (x₀, y₀) to Ax + By + C = 0:\nd = |A x₀ + B y₀ + C| / √(A² + B²)",
            description = "Fundamental analytic geometry formulas for points, distance, slopes, and straight lines.",
            variables = listOf("(x₁, y₁), (x₂, y₂) = coordinates", "m = slope", "d = perpendicular distance"),
            example = "Distance between (1, 2) and (4, 6) = √(3² + 4²) = √25 = 5",
            tags = listOf("coordinate geometry", "distance formula", "slope", "perpendicular distance")
        ),
        Formula(
            id = "geom_conic_sections",
            title = "Conic Sections: Parabola, Ellipse, Hyperbola",
            category = FormulaCategory.GEOMETRY,
            subcategory = "Conics",
            formulaDisplay = "Circle:  (x - h)² + (y - k)² = r²\nParabola:  y² = 4ax  (Focus: (a, 0), Directrix: x = -a)\nEllipse:  x²/a² + y²/b² = 1  (a > b)\n  • Eccentricity: e = √(1 - b²/a²) < 1\n  • Foci: (±ae, 0),  Area = πab\nHyperbola:  x²/a² - y²/b² = 1\n  • Eccentricity: e = √(1 + b²/a²) > 1\n  • Asymptotes: y = ±(b/a)x",
            description = "Standard cartesian equations, eccentricity, foci, and geometric properties of conic sections.",
            variables = listOf("(h, k) = center/vertex", "a, b = semi-major/minor axes", "e = eccentricity"),
            example = "Ellipse x²/25 + y²/9 = 1: a=5, b=3 → e = √(1 - 9/25) = 4/5 = 0.8, Foci = (±4, 0)",
            tags = listOf("conics", "circle", "parabola", "ellipse", "hyperbola", "foci")
        ),
        Formula(
            id = "geom_2d_areas_perimeters",
            title = "2D Shapes: Areas & Perimeters",
            category = FormulaCategory.GEOMETRY,
            subcategory = "Mensuration",
            formulaDisplay = "Circle:  Area = πr²,  Circumference = 2πr\nTriangle:  Area = ½ · base · height\nTrapezoid:  Area = ½(a + b)h\nParallelogram:  Area = b · h\nRegular n-gon:  Area = ½ · Perimeter · Apothem = ¼ n s² cot(π/n)",
            description = "Area and perimeter calculations for two-dimensional planar figures.",
            variables = listOf("r = radius", "a, b = parallel bases", "h = perpendicular height"),
            example = "Trapezoid with bases 8 and 12, height 5: Area = 0.5(8 + 12)(5) = 50",
            tags = listOf("area", "perimeter", "circle", "triangle", "trapezoid")
        ),
        Formula(
            id = "geom_3d_solids",
            title = "3D Solids: Volumes & Surface Areas",
            category = FormulaCategory.GEOMETRY,
            subcategory = "Mensuration",
            formulaDisplay = "Sphere:  V = (4/3)πr³,  Surface Area = 4πr²\nCylinder:  V = πr²h,  Total Surface Area = 2πr(h + r)\nCone:  V = (1/3)πr²h,  Slant Height l = √(r² + h²)\n  • Lateral Area = πrl,  Total Area = πr(l + r)\nFrustum of Cone:  V = (1/3)πh (R² + r² + Rr)\nCuboid:  V = l·w·h,  Surface Area = 2(lw + lh + wh)",
            description = "Volume, curved surface area, and total surface area formulas for three-dimensional geometry.",
            variables = listOf("r, R = radii", "h = height", "l = slant height"),
            example = "Sphere of radius 3: V = (4/3)π(27) = 36π ≈ 113.10, Area = 4π(9) = 36π ≈ 113.10",
            tags = listOf("volume", "surface area", "sphere", "cylinder", "cone", "frustum")
        ),
        Formula(
            id = "geom_vector_products",
            title = "Vector Dot, Cross, & Scalar Triple Product",
            category = FormulaCategory.GEOMETRY,
            subcategory = "Vectors",
            formulaDisplay = "Dot Product:  a · b = |a||b| cos θ = a₁b₁ + a₂b₂ + a₃b₃\nCross Product:  |a × b| = |a||b| sin θ\na × b = (a₂b₃ - a₃b₂)i - (a₁b₃ - a₃b₁)j + (a₁b₂ - a₂b₁)k\nScalar Triple Product (Volume of parallelepiped):\n[a b c] = a · (b × c) = det([a₁ a₂ a₃; b₁ b₂ b₃; c₁ c₂ c₃])\nAngle between vectors:  cos θ = (a · b) / (|a| |b|)",
            description = "Vector algebra, projections, cross product determinants, and volume calculations.",
            variables = listOf("a, b, c = 3D vectors", "θ = angle between vectors"),
            example = "a = (1, 2, 3), b = (4, -5, 6) → a · b = 4 - 10 + 18 = 12",
            tags = listOf("dot product", "cross product", "vectors", "scalar triple product")
        ),

        // ==========================================
        // 6. STATISTICS & PROBABILITY
        // ==========================================
        Formula(
            id = "stat_mean_variance_sd",
            title = "Mean, Variance, & Standard Deviation",
            category = FormulaCategory.STATISTICS,
            subcategory = "Descriptive",
            formulaDisplay = "Mean (μ):  μ = (∑ xᵢ) / N\nPopulation Variance (σ²):  σ² = ∑ (xᵢ - μ)² / N = (∑ xᵢ² / N) - μ²\nSample Variance (s²):  s² = ∑ (xᵢ - x̄)² / (n - 1)\nStandard Deviation:  σ = √(Variance)\nCoefficient of Variation:  CV = (σ / μ) · 100%",
            description = "Standard measures of central tendency and dispersion for statistical datasets.",
            variables = listOf("xᵢ = individual values", "N = population size", "n = sample size"),
            example = "Values {2, 4, 6}: Mean = 4, Variance = ((4+0+4)/3) = 8/3 ≈ 2.67, σ ≈ 1.63",
            tags = listOf("mean", "variance", "standard deviation", "dispersion")
        ),
        Formula(
            id = "stat_probability_bayes",
            title = "Conditional Probability & Bayes' Theorem",
            category = FormulaCategory.STATISTICS,
            subcategory = "Probability",
            formulaDisplay = "Addition Rule:  P(A ∪ B) = P(A) + P(B) - P(A ∩ B)\nConditional Probability:  P(A | B) = P(A ∩ B) / P(B)\nIndependent Events:  P(A ∩ B) = P(A) · P(B)\nBayes' Theorem:\nP(Aᵢ | B) = [P(B | Aᵢ) P(Aᵢ)] / [∑ [j=1 to k] P(B | Aⱼ) P(Aⱼ)]",
            description = "Core rules of probability and updating prior probabilities using Bayes' Theorem.",
            variables = listOf("P(A) = prior probability", "P(A|B) = posterior probability"),
            example = "P(A)=0.5, P(B|A)=0.8, P(B|A')=0.1 → P(A|B) = (0.8·0.5)/[(0.8·0.5)+(0.1·0.5)] = 0.4/0.45 ≈ 0.889",
            tags = listOf("probability", "bayes theorem", "conditional probability")
        ),
        Formula(
            id = "stat_binomial_poisson",
            title = "Binomial & Poisson Probability Distributions",
            category = FormulaCategory.STATISTICS,
            subcategory = "Distributions",
            formulaDisplay = "Binomial Distribution B(n, p):\nP(X = k) = (ⁿCₖ) pᵏ (1 - p)ⁿ⁻ᵏ\n• Mean μ = n·p,  Variance σ² = n·p(1 - p)\n\nPoisson Distribution (λ):\nP(X = k) = (λᵏ e⁻ᵝ) / k!\n• Mean μ = λ,  Variance σ² = λ",
            description = "Discrete probability models for independent Bernoulli trials and rare arrival events.",
            variables = listOf("n = trials", "p = success probability", "k = number of successes", "λ = average rate"),
            example = "10 coin flips, exactly 5 heads: P = (¹⁰C₅)(0.5)⁵(0.5)⁵ = 252/1024 ≈ 0.2461",
            tags = listOf("binomial distribution", "poisson distribution", "bernoulli")
        ),
        Formula(
            id = "stat_normal_distribution",
            title = "Normal (Gaussian) Distribution",
            category = FormulaCategory.STATISTICS,
            subcategory = "Distributions",
            formulaDisplay = "Probability Density Function (PDF):\nf(x) = [1 / (σ √(2π))] · e^[ -½ ((x - μ) / σ)² ]\nStandard Normal Z-Score:\nZ = (X - μ) / σ\nEmpirical 68-95-99.7 Rule:\n• P(μ - σ ≤ X ≤ μ + σ) ≈ 68.27%\n• P(μ - 2σ ≤ X ≤ μ + 2σ) ≈ 95.45%\n• P(μ - 3σ ≤ X ≤ μ + 3σ) ≈ 99.73%",
            description = "The central probability distribution in statistics and empirical confidence bands.",
            variables = listOf("μ = mean", "σ = standard deviation", "Z = standardized z-score"),
            example = "X = 115, μ = 100, σ = 15 → Z = (115 - 100)/15 = +1.0 (84th percentile)",
            tags = listOf("normal distribution", "gaussian", "bell curve", "z score")
        ),

        // ==========================================
        // 7. LINEAR ALGEBRA & MATRICES
        // ==========================================
        Formula(
            id = "mat_determinant_inverse",
            title = "Matrix Determinants & Inverse Matrix",
            category = FormulaCategory.MATRICES,
            subcategory = "Matrices",
            formulaDisplay = "For 2×2 Matrix A = [a b; c d]:\nDet(A) = |A| = ad - bc\nA⁻¹ = (1 / Det(A)) · [d -b; -c a]  (Det(A) ≠ 0)\n\nFor 3×3 Matrix A = [a b c; d e f; g h i]:\nDet(A) = a(ei - fh) - b(di - fg) + c(dh - eg)\nProperties:  Det(AB) = Det(A) · Det(B),  Det(A⁻¹) = 1 / Det(A)",
            description = "Calculates matrix determinants, invertibility criteria, and analytic inverse formulas.",
            variables = listOf("a, b, c, d... = matrix entries", "Det(A) = scalar determinant"),
            example = "A = [4 7; 2 6] → Det = 24 - 14 = 10 → A⁻¹ = 0.1 · [6 -7; -2 4]",
            tags = listOf("matrix", "determinant", "inverse", "invertible")
        ),
        Formula(
            id = "mat_eigenvalues_characteristic",
            title = "Eigenvalues & Characteristic Equation",
            category = FormulaCategory.MATRICES,
            subcategory = "Spectral Theory",
            formulaDisplay = "Characteristic Equation:\ndet(A - λI) = 0\nEigenvector Condition:  A v = λ v  (v ≠ 0)\n\nFor 2×2 Matrix A:\nλ² - Tr(A) λ + Det(A) = 0\nwhere Tr(A) = a + d (Trace), Det(A) = ad - bc",
            description = "Determines matrix eigenvalues (characteristic roots) and non-trivial eigenvectors.",
            variables = listOf("A = square matrix", "λ = eigenvalue scalar", "I = identity matrix", "v = eigenvector"),
            example = "A = [2 1; 1 2] → Tr=4, Det=3 → λ² - 4λ + 3 = 0 → λ₁ = 3, λ₂ = 1",
            tags = listOf("eigenvalues", "eigenvectors", "characteristic equation", "trace")
        ),
        Formula(
            id = "mat_cramers_rule",
            title = "Cramer's Rule for Linear Systems",
            category = FormulaCategory.MATRICES,
            subcategory = "Linear Systems",
            formulaDisplay = "For system Ax = b:\nxᵢ = Det(Aᵢ) / Det(A)  (provided Det(A) ≠ 0)\nwhere Aᵢ is matrix A with column i replaced by column vector b.\n• If Det(A) ≠ 0: Unique solution\n• If Det(A) = 0 and Det(Aᵢ) = 0: Infinitely many solutions\n• If Det(A) = 0 and Det(Aᵢ) ≠ 0: Inconsistent (No solution)",
            description = "Explicit determinant ratio method for solving systems of linear equations.",
            variables = listOf("A = coefficient matrix", "b = constant vector", "xᵢ = variable values"),
            example = "3x + 2y = 12, x - y = 1: D = -5, Dx = -14, Dy = -9 → x = 2.8, y = 1.8",
            tags = listOf("cramers rule", "linear systems", "determinant")
        ),

        // ==========================================
        // 8. COMPLEX NUMBERS
        // ==========================================
        Formula(
            id = "cplx_euler_polar_form",
            title = "Polar & Euler Form of Complex Numbers",
            category = FormulaCategory.COMPLEX_NUMBERS,
            subcategory = "Representations",
            formulaDisplay = "Cartesian Form:  z = x + iy  (i² = -1)\nModulus (Magnitude):  |z| = r = √(x² + y²)\nArgument:  θ = arg(z) = atan2(y, x)\nPolar Form:  z = r(cos θ + i sin θ) = r cis(θ)\nEuler's Formula:  e^(iθ) = cos θ + i sin θ\nExponential Form:  z = r e^(iθ)\nEuler's Identity:  e^(iπ) + 1 = 0",
            description = "Fundamental bridge between algebraic complex numbers, trigonometry, and complex exponentials.",
            variables = listOf("x = real part", "y = imaginary part", "r = modulus", "θ = argument"),
            example = "1 + i√3: r = √(1+3) = 2, θ = 60° (π/3) → z = 2 e^(iπ/3)",
            tags = listOf("complex numbers", "polar form", "euler formula", "eulers identity")
        ),
        Formula(
            id = "cplx_demoivre_roots_of_unity",
            title = "De Moivre's Theorem & nth Roots of Unity",
            category = FormulaCategory.COMPLEX_NUMBERS,
            subcategory = "Powers & Roots",
            formulaDisplay = "De Moivre's Theorem:\n[r (cos θ + i sin θ)]ⁿ = rⁿ (cos(nθ) + i sin(nθ)) = rⁿ e^(i n θ)\n\nnth Roots of Complex Number z = r e^(iθ):\nz_k = ⁿ√r · [cos((θ + 2kπ)/n) + i sin((θ + 2kπ)/n)]\nfor k = 0, 1, 2, ..., n-1\n\nnth Roots of Unity (zⁿ = 1):\nω_k = e^(i 2kπ / n)  (Form vertices of a regular n-gon in the Argand plane)",
            description = "Calculates integer powers and all distinct nth complex roots of unity.",
            variables = listOf("n = positive integer degree", "k = root index (0 to n-1)"),
            example = "Cube roots of 1: 1, e^(i 2π/3) = -½ + i√3/2, e^(i 4π/3) = -½ - i√3/2",
            tags = listOf("de moivre", "roots of unity", "complex roots", "powers")
        )
    )

    val SOLVED_EQUATIONS: List<SolvedEquation> = listOf(
        SolvedEquation(
            id = "solved_quad_1",
            title = "Quadratic Equation with Real Roots",
            topic = "Algebra",
            problem = "Solve for x:  2x² - 4x - 6 = 0",
            steps = listOf(
                SolutionStep(1, "Identify coefficients in ax² + bx + c = 0", "a = 2, b = -4, c = -6"),
                SolutionStep(2, "Calculate Discriminant Δ = b² - 4ac", "Δ = (-4)² - 4(2)(-6) = 16 + 48 = 64"),
                SolutionStep(3, "Apply the Quadratic Formula x = (-b ± √Δ) / (2a)", "x = (-(-4) ± √64) / (2 · 2) = (4 ± 8) / 4"),
                SolutionStep(4, "Separate into two real solutions", "x₁ = (4 + 8) / 4 = 12 / 4 = 3\nx₂ = (4 - 8) / 4 = -4 / 4 = -1")
            ),
            finalAnswer = "x = 3  or  x = -1",
            keyTakeaway = "Since Δ = 64 > 0, the parabola intersects the x-axis at two distinct real points."
        ),
        SolvedEquation(
            id = "solved_quad_complex",
            title = "Quadratic Equation with Complex Roots",
            topic = "Algebra",
            problem = "Solve for x:  x² - 4x + 13 = 0",
            steps = listOf(
                SolutionStep(1, "Identify coefficients", "a = 1, b = -4, c = 13"),
                SolutionStep(2, "Calculate Discriminant Δ = b² - 4ac", "Δ = (-4)² - 4(1)(13) = 16 - 52 = -36"),
                SolutionStep(3, "Recognize negative discriminant (no real roots)", "√(-36) = √36 · √(-1) = 6i"),
                SolutionStep(4, "Apply Quadratic Formula", "x = (-(-4) ± 6i) / (2 · 1) = (4 ± 6i) / 2 = 2 ± 3i")
            ),
            finalAnswer = "x = 2 + 3i  or  x = 2 - 3i",
            keyTakeaway = "Complex roots always occur in conjugate pairs (a ± bi) when polynomial coefficients are real."
        ),
        SolvedEquation(
            id = "solved_system_2x2",
            title = "System of 2 Linear Equations (Cramer's Rule)",
            topic = "Linear Algebra",
            problem = "Solve the system:\n1) 3x + 2y = 12\n2)  x -  y = 1",
            steps = listOf(
                SolutionStep(1, "Form matrix determinants", "D = |3  2;  1 -1| = 3(-1) - 2(1) = -3 - 2 = -5"),
                SolutionStep(2, "Calculate Dx by replacing x-column with constants", "Dx = |12  2;  1 -1| = 12(-1) - 2(1) = -12 - 2 = -14"),
                SolutionStep(3, "Calculate Dy by replacing y-column with constants", "Dy = |3  12;  1   1| = 3(1) - 12(1) = 3 - 12 = -9"),
                SolutionStep(4, "Find x and y using Cramer's ratios", "x = Dx / D = -14 / -5 = 2.8 (or 14/5)\ny = Dy / D = -9 / -5 = 1.8 (or 9/5)")
            ),
            finalAnswer = "x = 2.8,  y = 1.8",
            keyTakeaway = "Cramer's rule evaluates unique solutions when the main coefficient determinant D ≠ 0."
        ),
        SolvedEquation(
            id = "solved_radical_simplification",
            title = "Radical Simplification (Square Root)",
            topic = "Squares & Roots",
            problem = "Simplify the radical completely:  √72",
            steps = listOf(
                SolutionStep(1, "Find prime factorization of 72", "72 = 2 · 36 = 2 · 6² = 2³ · 3²"),
                SolutionStep(2, "Group into square pairs", "72 = (2² · 3²) · 2 = (4 · 9) · 2 = 36 · 2"),
                SolutionStep(3, "Extract the square root of perfect squares", "√72 = √(36 · 2) = √36 · √2 = 6√2"),
                SolutionStep(4, "Decimal approximation check", "6 · (1.4142) ≈ 8.4853")
            ),
            finalAnswer = "6√2  (≈ 8.4853)",
            keyTakeaway = "Always factor out the largest perfect square factor to put a radical in simplest form."
        ),
        SolvedEquation(
            id = "solved_cbrt_simplification",
            title = "Cube Root Simplification",
            topic = "Squares & Roots",
            problem = "Simplify the cube root:  ∛108",
            steps = listOf(
                SolutionStep(1, "Prime factorize 108", "108 = 2² · 3³ = 4 · 27"),
                SolutionStep(2, "Identify perfect cube factors (power of 3)", "27 is 3³"),
                SolutionStep(3, "Extract the cube root of the perfect cube", "∛108 = ∛(27 · 4) = ∛27 · ∛4 = 3∛4"),
                SolutionStep(4, "Verify decimal equivalent", "3 · (4)^(1/3) = 3 · 1.5874 ≈ 4.7622")
            ),
            finalAnswer = "3∛4  (≈ 4.7622)",
            keyTakeaway = "For cube roots, extract factors that have exponents of 3 (cubes)."
        ),
        SolvedEquation(
            id = "solved_derivative_product",
            title = "Derivative by Product Rule",
            topic = "Calculus",
            problem = "Find f'(x) for:  f(x) = x² sin(x)",
            steps = listOf(
                SolutionStep(1, "Identify parts u(x) and v(x)", "u = x²,  v = sin(x)"),
                SolutionStep(2, "Compute individual derivatives u' and v'", "u' = d/dx(x²) = 2x\nv' = d/dx(sin x) = cos(x)"),
                SolutionStep(3, "Apply Product Rule formula (uv)' = u'v + uv'", "f'(x) = (2x)(sin x) + (x²)(cos x)"),
                SolutionStep(4, "Factor out common terms", "f'(x) = x(2 sin x + x cos x)")
            ),
            finalAnswer = "f'(x) = 2x sin(x) + x² cos(x) = x(2 sin x + x cos x)",
            keyTakeaway = "Remember never to simply multiply derivatives together: d/dx(uv) ≠ u'v'."
        ),
        SolvedEquation(
            id = "solved_integral_substitution",
            title = "Integration by Substitution (u-sub)",
            topic = "Calculus",
            problem = "Evaluate indefinite integral:  ∫ 2x cos(x²) dx",
            steps = listOf(
                SolutionStep(1, "Choose substitution variable u", "Let u = x²"),
                SolutionStep(2, "Compute differential du", "du = 2x dx  →  dx = du / (2x)"),
                SolutionStep(3, "Substitute into the integral", "∫ 2x cos(u) (du / 2x) = ∫ cos(u) du"),
                SolutionStep(4, "Integrate with respect to u", "∫ cos(u) du = sin(u) + C"),
                SolutionStep(5, "Back-substitute u = x²", "sin(x²) + C")
            ),
            finalAnswer = "sin(x²) + C",
            keyTakeaway = "Look for an inner expression whose derivative appears as an outside multiplier factor."
        ),
        SolvedEquation(
            id = "solved_integral_parts",
            title = "Integration by Parts (LIATE Rule)",
            topic = "Calculus",
            problem = "Evaluate:  ∫ x e²ˣ dx",
            steps = listOf(
                SolutionStep(1, "Choose u and dv via LIATE rule", "u = x (algebraic),  dv = e²ˣ dx (exponential)"),
                SolutionStep(2, "Differentiate u and integrate dv", "du = dx\nv = ∫ e²ˣ dx = ½ e²ˣ"),
                SolutionStep(3, "Apply formula: ∫ u dv = uv - ∫ v du", "∫ x e²ˣ dx = x(½ e²ˣ) - ∫ (½ e²ˣ) dx"),
                SolutionStep(4, "Evaluate remaining integral", "= ½ x e²ˣ - ½ (½ e²ˣ) + C = ½ x e²ˣ - ¼ e²ˣ + C"),
                SolutionStep(5, "Factor out common terms", "= ¼ e²ˣ (2x - 1) + C")
            ),
            finalAnswer = "¼ e²ˣ (2x - 1) + C",
            keyTakeaway = "Differentiating x reduces its algebraic degree to 1, leaving an elementary exponential integral."
        )
    )
}
