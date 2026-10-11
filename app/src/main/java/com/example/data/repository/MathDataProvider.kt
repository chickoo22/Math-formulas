package com.example.data.repository

import com.example.data.model.Formula
import com.example.data.model.MathCategory
import com.example.data.model.SolvedEquation
import com.example.data.model.SolutionStep

object MathDataProvider {

    val FORMULAS: List<Formula> = listOf(
        // ROOTS & POWERS
        Formula(
            id = "roots_laws_exp",
            title = "Laws of Exponents",
            formulaDisplay = "aᵐ · aⁿ = aᵐ⁺ⁿ  |  aᵐ / aⁿ = aᵐ⁻ⁿ  |  (aᵐ)ⁿ = aᵐⁿ",
            description = "Fundamental algebraic operations for multiplying, dividing, and exponentiating terms with the same base.",
            category = MathCategory.ROOTS_POWERS,
            subcategory = "Exponents",
            variables = listOf("a: Base (a ≠ 0)", "m, n: Exponents"),
            example = "2³ · 2⁴ = 2³⁺⁴ = 2⁷ = 128",
            memoryTip = "Multiplying like bases? ADD powers. Dividing? SUBTRACT. Power of power? MULTIPLY.",
            isHighYield = true,
            searchTags = listOf("powers", "indices", "multiplication", "rules")
        ),
        Formula(
            id = "roots_fractional_exp",
            title = "Fractional Exponents & Radicals",
            formulaDisplay = "a¹ᐟⁿ = ⁿ√a   |   aᵐᐟⁿ = ⁿ√(aᵐ) = (ⁿ√a)ᵐ",
            description = "Converts between fractional power indices and standard radical root representations.",
            category = MathCategory.ROOTS_POWERS,
            subcategory = "Radicals",
            variables = listOf("a: Radicand (a ≥ 0 for even n)", "n: Index of radical (n ≥ 2)", "m: Exponent"),
            example = "8²ᐟ³ = (³√8)² = 2² = 4",
            memoryTip = "Flower Power rule: Power on top (sun/flower), Root on bottom (underground root) -> a^(power/root).",
            isHighYield = true,
            searchTags = listOf("fractional", "root", "power", "radical")
        ),
        Formula(
            id = "roots_product_quotient",
            title = "Product & Quotient of Radicals",
            formulaDisplay = "ⁿ√(a · b) = ⁿ√a · ⁿ√b   |   ⁿ√(a / b) = ⁿ√a / ⁿ√b",
            description = "Splits or combines radicals with identical indices under multiplication and division.",
            category = MathCategory.ROOTS_POWERS,
            subcategory = "Radicals",
            variables = listOf("a, b: Radicands (b ≠ 0)", "n: Positive integer root index"),
            example = "√72 = √(36 · 2) = √36 · √2 = 6√2",
            memoryTip = "You can split roots under multiplication and division, but NEVER under addition: √(a+b) ≠ √a + √b!",
            isHighYield = true,
            searchTags = listOf("simplify", "radical", "square root", "cube root")
        ),
        Formula(
            id = "roots_rationalization",
            title = "Radical Conjugate Rationalization",
            formulaDisplay = "1 / (√a + √b) = (√a - √b) / (a - b)",
            description = "Eliminates square root terms from denominators by multiplying numerator and denominator by the conjugate.",
            category = MathCategory.ROOTS_POWERS,
            subcategory = "Simplification",
            variables = listOf("a, b: Real numbers with a ≠ b"),
            example = "1 / (√5 + √2) = (√5 - √2) / (5 - 2) = (√5 - √2) / 3",
            memoryTip = "Conjugate flips the middle sign: (√a + √b)(√a - √b) = a - b. The radical completely vanishes from the bottom!",
            isHighYield = true,
            searchTags = listOf("conjugate", "rationalize", "fraction", "denominator")
        ),
        Formula(
            id = "roots_nested_radicals",
            title = "De-nesting Nested Radicals",
            formulaDisplay = "√(a ± √b) = √((a + c)/2) ± √((a - c)/2)   where c = √(a² - b)",
            description = "Simplifies double nested radicals into the sum or difference of two simple square roots when a² - b is a perfect square.",
            category = MathCategory.ROOTS_POWERS,
            subcategory = "Nested Roots",
            variables = listOf("a: First term", "b: Radicand under inner root", "c: √(a² - b) must be rational"),
            example = "√(5 + 2√6) = √(5 + √24) -> c = √(25 - 24) = 1 -> √((5+1)/2) + √((5-1)/2) = √3 + √2",
            memoryTip = "Before solving, check c = √(a² - b). If c is a clean whole number, de-nesting will work perfectly!",
            isHighYield = false,
            searchTags = listOf("nested", "double root", "ramanujan", "surd")
        ),
        Formula(
            id = "roots_sum_cubes",
            title = "Sum and Difference of Cubes",
            formulaDisplay = "a³ ± b³ = (a ± b)(a² ∓ ab + b²)",
            description = "Canonical polynomial factorization formula for binomial cubes.",
            category = MathCategory.ROOTS_POWERS,
            subcategory = "Cubes",
            variables = listOf("a, b: Arbitrary algebraic expressions or numbers"),
            example = "x³ - 8 = x³ - 2³ = (x - 2)(x² + 2x + 4)",
            memoryTip = "SOAP mnemonic for signs: Same sign first, Opposite sign middle, Always Positive at the end!",
            isHighYield = true,
            searchTags = listOf("cube", "factorization", "algebra", "identity")
        ),

        // ALGEBRA
        Formula(
            id = "alg_quadratic",
            title = "Quadratic Formula & Discriminant",
            formulaDisplay = "x = (-b ± √(b² - 4ac)) / (2a)   |   Δ = b² - 4ac",
            description = "Calculates exact solutions for any quadratic equation ax² + bx + c = 0. Δ > 0 yields 2 real roots, Δ = 0 gives 1 root, Δ < 0 gives complex conjugates.",
            category = MathCategory.ALGEBRA,
            subcategory = "Equations",
            variables = listOf("a: Leading coefficient (a ≠ 0)", "b: Linear coefficient", "c: Constant term", "Δ: Discriminant"),
            example = "x² - 5x + 6 = 0 -> a=1, b=-5, c=6 -> Δ = 25 - 24 = 1 -> x = (5 ± 1)/2 -> x = 3, 2",
            memoryTip = "Story mnemonic: A negative boy (-b) was undecided (±) about a radical party (√) because he was square (b²) and missed 4 awesome chicks (-4ac), and the party ended at 2 a.m. (2a).",
            isHighYield = true,
            searchTags = listOf("quadratic", "roots", "parabola", "discriminant")
        ),
        Formula(
            id = "alg_vieta",
            title = "Vieta's Formulas (Quadratic)",
            formulaDisplay = "x₁ + x₂ = -b / a   |   x₁ · x₂ = c / a",
            description = "Relates polynomial coefficients directly to the sum and product of equation roots without solving for x.",
            category = MathCategory.ALGEBRA,
            subcategory = "Roots Relation",
            variables = listOf("x₁, x₂: Roots of ax² + bx + c = 0"),
            example = "For 2x² - 8x + 6 = 0: Sum = -(-8)/2 = 4, Product = 6/2 = 3",
            memoryTip = "Sum changes sign (-b/a); Product keeps the sign (+c/a). High-yield for rapid multiple-choice checks!",
            isHighYield = true,
            searchTags = listOf("vieta", "sum of roots", "product of roots")
        ),
        Formula(
            id = "alg_binomial",
            title = "Binomial Theorem",
            formulaDisplay = "(x + y)ⁿ = ∑ₖ₌₀ⁿ [ n! / (k!(n - k)!) ] · xⁿ⁻ᵏ · yᵏ",
            description = "Expands the power of any two-term algebraic expression using combinatorial binomial coefficients.",
            category = MathCategory.ALGEBRA,
            subcategory = "Expansion",
            variables = listOf("n: Non-negative integer power", "k: Index from 0 to n"),
            example = "(x + 2)³ = x³ + 3(x²)(2) + 3(x)(4) + 8 = x³ + 6x² + 12x + 8",
            memoryTip = "Power of x counts down from n to 0; power of y counts up from 0 to n. Sum of powers is always n!",
            isHighYield = true,
            searchTags = listOf("binomial", "pascal", "combinatorics", "series")
        ),
        Formula(
            id = "alg_ap_gp",
            title = "Arithmetic & Geometric Progressions",
            formulaDisplay = "AP: aₙ = a₁ + (n-1)d, Sₙ = (n/2)(2a₁ + (n-1)d)  |  GP: Sₙ = a₁(1 - rⁿ)/(1 - r)",
            description = "Term and sum formulas for discrete linear step sequences (AP) and multiplicative ratio series (GP).",
            category = MathCategory.ALGEBRA,
            subcategory = "Sequences",
            variables = listOf("a₁: First term", "d: Common difference", "r: Common ratio (r ≠ 1)", "n: Number of terms"),
            example = "AP 3, 7, 11... d=4. a₁₀ = 3 + 9(4) = 39. S₁₀ = (10/2)(3 + 39) = 210",
            memoryTip = "AP sums are average of first and last multiplied by count: S = n · (first + last) / 2.",
            isHighYield = true,
            searchTags = listOf("sequence", "series", "progression", "ap", "gp")
        ),
        Formula(
            id = "alg_log_laws",
            title = "Logarithm Laws & Base Change",
            formulaDisplay = "log(xy) = log x + log y   |   log(x/y) = log x - log y   |   log_b(a) = ln a / ln b",
            description = "Algebraic rules for decomposing logarithmic products, fractions, power arguments, and converting base.",
            category = MathCategory.ALGEBRA,
            subcategory = "Logarithms",
            variables = listOf("x, y > 0", "b: Base (b > 0, b ≠ 1)"),
            example = "log₂(32) = ln(32) / ln(2) = 5",
            memoryTip = "Logs turn multiplication into addition, division into subtraction, and powers into multipliers in front!",
            isHighYield = true,
            searchTags = listOf("log", "natural log", "ln", "exponent")
        ),

        // TRIGONOMETRY
        Formula(
            id = "trig_pythagorean",
            title = "Pythagorean Trigonometric Identities",
            formulaDisplay = "sin²θ + cos²θ = 1   |   1 + tan²θ = sec²θ   |   1 + cot²θ = csc²θ",
            description = "Core circular trigonometry equalities derived directly from the unit circle radius vector.",
            category = MathCategory.TRIGONOMETRY,
            subcategory = "Identities",
            variables = listOf("θ: Angle in radians or degrees"),
            example = "If sin θ = 3/5 in Quadrant I: cos θ = √(1 - 9/25) = 4/5",
            memoryTip = "Divide sin² + cos² = 1 by cos² to get 1 + tan² = sec². Divide by sin² to get 1 + cot² = csc²!",
            isHighYield = true,
            searchTags = listOf("sin", "cos", "tan", "pythagoras", "unit circle")
        ),
        Formula(
            id = "trig_angle_sum",
            title = "Angle Sum & Difference Identities",
            formulaDisplay = "sin(α ± β) = sin α cos β ± cos α sin β  |  cos(α ± β) = cos α cos β ∓ sin α sin β",
            description = "Decomposes compound trigonometric arguments into products of individual angle functions.",
            category = MathCategory.TRIGONOMETRY,
            subcategory = "Compound Angles",
            variables = listOf("α, β: Angles"),
            example = "sin(75°) = sin(45° + 30°) = (√2/2)(√3/2) + (√2/2)(1/2) = (√6 + √2)/4",
            memoryTip = "Sine is friendly (mixes sin and cos, keeps same sign); Cosine is selfish (keeps cos together, then sin, and flips the sign!).",
            isHighYield = true,
            searchTags = listOf("compound", "sum", "difference", "sine", "cosine")
        ),
        Formula(
            id = "trig_double_angle",
            title = "Double-Angle Formulas",
            formulaDisplay = "sin(2θ) = 2 sin θ cos θ   |   cos(2θ) = cos²θ - sin²θ = 2cos²θ - 1 = 1 - 2sin²θ",
            description = "Expresses trigonometric functions of 2θ in terms of degree-1 functions of θ.",
            category = MathCategory.TRIGONOMETRY,
            subcategory = "Double Angles",
            variables = listOf("θ: Angle"),
            example = "If cos θ = 1/√2: cos(2θ) = 2(1/2) - 1 = 0 (since 2θ = 90°)",
            memoryTip = "cos(2θ) has 3 forms: cos² - sin², 2cos² - 1, and 1 - 2sin². Pick whichever cancels terms in your problem!",
            isHighYield = true,
            searchTags = listOf("double angle", "power reduction", "sine", "cosine")
        ),
        Formula(
            id = "trig_law_sines_cosines",
            title = "Law of Sines & Law of Cosines",
            formulaDisplay = "a / sin A = b / sin B = c / sin C = 2R   |   c² = a² + b² - 2ab cos C",
            description = "Solves arbitrary acute or obtuse triangles given side-angle-side or angle-side combinations.",
            category = MathCategory.TRIGONOMETRY,
            subcategory = "Triangle Laws",
            variables = listOf("a, b, c: Triangle side lengths", "A, B, C: Opposite angles", "R: Circumcircle radius"),
            example = "a = 5, b = 7, C = 60°: c² = 25 + 49 - 2(5)(7)(0.5) = 74 - 35 = 39 -> c = √39",
            memoryTip = "Law of Cosines is just Pythagorean theorem with a correction term: c² = a² + b² - 2ab·cos(C).",
            isHighYield = true,
            searchTags = listOf("triangle", "sine rule", "cosine rule", "geometry")
        ),

        // CALCULUS
        Formula(
            id = "calc_derivative_rules",
            title = "Product, Quotient & Chain Rules",
            formulaDisplay = "(uv)' = u'v + uv'   |   (u/v)' = (u'v - uv') / v²   |   d/dx[f(g(x))] = f'(g(x)) · g'(x)",
            description = "Foundational differential calculus rules for multiplying, dividing, and composing differentiable functions.",
            category = MathCategory.CALCULUS,
            subcategory = "Differentiation",
            variables = listOf("u, v: Differentiable functions of x", "f, g: Composite functions"),
            example = "d/dx[x² sin x] = 2x sin x + x² cos x",
            memoryTip = "Quotient rule rhyme: 'Low d-High minus High d-Low, over the square of what's below!' (v·u' - u·v') / v².",
            isHighYield = true,
            searchTags = listOf("derivative", "chain rule", "quotient", "product")
        ),
        Formula(
            id = "calc_integration_parts",
            title = "Integration by Parts",
            formulaDisplay = "∫ u · dv = u · v - ∫ v · du",
            description = "Integral counterpart of the product rule; transforms the integral of a function product into a simpler evaluation.",
            category = MathCategory.CALCULUS,
            subcategory = "Integration",
            variables = listOf("u, v: Differentiable functions. Choose u using LIATE rule (Log, Inverse trig, Algebraic, Trig, Exponential)."),
            example = "∫ x eˣ dx: u = x, dv = eˣ dx -> du = dx, v = eˣ -> x eˣ - ∫ eˣ dx = x eˣ - eˣ + C",
            memoryTip = "LIATE acronym for choosing u: Logarithmic > Inverse Trig > Algebraic > Trig > Exponential. Differentiate u, integrate dv!",
            isHighYield = true,
            searchTags = listOf("integral", "parts", "calculus", "liate")
        ),
        Formula(
            id = "calc_ftc",
            title = "Fundamental Theorem of Calculus",
            formulaDisplay = "d/dx [∫ₐˣ f(t) dt] = f(x)   |   ∫ₐᵇ f(x) dx = F(b) - F(a)  where F'(x) = f(x)",
            description = "Bridges the concept of differentiation with continuous accumulation and area under a curve.",
            category = MathCategory.CALCULUS,
            subcategory = "Theorems",
            variables = listOf("f: Continuous function on [a, b]", "F: Antiderivative of f"),
            example = "∫₀² 3x² dx = [x³]₀² = 2³ - 0³ = 8",
            memoryTip = "Derivative undoes integral, and definite integral is simply upper limit value minus lower limit value: F(b) - F(a).",
            isHighYield = true,
            searchTags = listOf("ftc", "definite integral", "area", "antiderivative")
        ),
        Formula(
            id = "calc_taylor",
            title = "Taylor & Maclaurin Series",
            formulaDisplay = "f(x) = ∑ₙ₌₀^∞ [ f⁽ⁿ⁾(a) / n! ] · (x - a)ⁿ   |   Maclaurin: a = 0",
            description = "Approximates any infinitely differentiable function as an infinite polynomial expansion around a center point.",
            category = MathCategory.CALCULUS,
            subcategory = "Series",
            variables = listOf("f⁽ⁿ⁾(a): n-th derivative evaluated at center a", "n!: Factorial"),
            example = "eˣ = 1 + x + x²/2! + x³/3! + ... ; sin x = x - x³/3! + x⁵/5! - ...",
            memoryTip = "Maclaurin is simply Taylor series centered at a = 0. Notice odd functions (sin) have odd powers, even (cos) have even powers!",
            isHighYield = true,
            searchTags = listOf("taylor", "maclaurin", "series", "approximation")
        ),
        Formula(
            id = "calc_ode_first_order",
            title = "First-Order Linear ODE (Integrating Factor)",
            formulaDisplay = "y' + P(x)y = Q(x)   ->   I(x) = e^(∫ P(x)dx)   ->   y = (1/I(x)) · ∫ I(x)Q(x)dx",
            description = "Exact analytic solution for any first-order linear ordinary differential equation using the integrating factor technique.",
            category = MathCategory.CALCULUS,
            subcategory = "Differential Equations",
            variables = listOf("P(x), Q(x): Continuous coefficient functions", "I(x): Integrating factor"),
            example = "y' + (1/x)y = x² -> I(x) = e^(ln x) = x -> y = (1/x) ∫ x³ dx = x³/4 + C/x",
            memoryTip = "Multiply entire equation by I(x) = e^(∫P dx) to make the left side a pure product rule derivative: d/dx[I · y]!",
            isHighYield = false,
            searchTags = listOf("ode", "differential equations", "integrating factor")
        ),

        // LINEAR ALGEBRA
        Formula(
            id = "la_matrix_mult",
            title = "Matrix Multiplication (2×2)",
            formulaDisplay = "[a b; c d] · [e f; g h] = [ae + bg, af + bh ; ce + dg, cf + dh]",
            description = "Row-by-column inner product operation for matrix composition and linear transformations.",
            category = MathCategory.LINEAR_ALGEBRA,
            subcategory = "Operations",
            variables = listOf("Row 1: [a, b], Row 2: [c, d]", "Column 1: [e, g], Column 2: [f, h]"),
            example = "[1 2; 3 4] · [2 0; 1 3] = [1(2)+2(1), 1(0)+2(3); 3(2)+4(1), 3(0)+4(3)] = [4 6; 10 12]",
            memoryTip = "Row dives into Column! Across the first matrix, DOWN the second matrix.",
            isHighYield = true,
            searchTags = listOf("matrix", "dot product", "multiplication", "transformation")
        ),
        Formula(
            id = "la_determinant",
            title = "Determinant of 2×2 & 3×3 Matrices",
            formulaDisplay = "det[a b; c d] = ad - bc   |   det(3x3): a(ei - fh) - b(di - fg) + c(dh - eg)",
            description = "Scalar value characterizing matrix invertibility, linear scaling factor, and geometric volume transformation.",
            category = MathCategory.LINEAR_ALGEBRA,
            subcategory = "Determinants",
            variables = listOf("det(A) ≠ 0 implies matrix A is non-singular and invertible"),
            example = "det[3 8; 4 6] = 3(6) - 8(4) = 18 - 32 = -14",
            memoryTip = "Main diagonal minus off diagonal: ad - bc. If det = 0, the matrix collapses space and cannot be inverted.",
            isHighYield = true,
            searchTags = listOf("determinant", "cramer", "invertible", "matrix")
        ),
        Formula(
            id = "la_inverse",
            title = "2×2 Matrix Inverse",
            formulaDisplay = "A⁻¹ = (1 / det(A)) · [d -b ; -c a]   where det(A) = ad - bc ≠ 0",
            description = "Calculates the exact multiplicative inverse of a 2x2 matrix using the adjugate matrix.",
            category = MathCategory.LINEAR_ALGEBRA,
            subcategory = "Inverses",
            variables = listOf("A: 2x2 matrix [a b; c d] with det(A) ≠ 0"),
            example = "[4 7; 2 6] -> det = 24 - 14 = 10 -> A⁻¹ = 0.1 · [6 -7; -2 4] = [0.6 -0.7; -0.2 0.4]",
            memoryTip = "Swap diagonal elements (a and d), negate off-diagonal elements (-b and -c), and divide all by determinant!",
            isHighYield = true,
            searchTags = listOf("inverse", "adjugate", "matrix", "linear")
        ),
        Formula(
            id = "la_eigenvalues",
            title = "Eigenvalues & Eigenvectors",
            formulaDisplay = "A · v = λ · v   |   Characteristic Eq: det(A - λ · I) = 0",
            description = "Finds non-zero vectors whose direction is invariant under linear transformation A, scaled by factor λ.",
            category = MathCategory.LINEAR_ALGEBRA,
            subcategory = "Spectral Theory",
            variables = listOf("A: Square matrix", "λ: Eigenvalue scalar", "v: Non-zero eigenvector", "I: Identity matrix"),
            example = "For [2 1; 1 2]: det[2-λ 1; 1 2-λ] = (2-λ)² - 1 = λ² - 4λ + 3 = 0 -> λ₁ = 3, λ₂ = 1",
            memoryTip = "Subtract λ along the diagonal, then set determinant to 0 to find the characteristic polynomial roots.",
            isHighYield = true,
            searchTags = listOf("eigenvalue", "eigenvector", "spectral", "diagonalization")
        ),

        // COMPLEX NUMBERS
        Formula(
            id = "cn_polar_euler",
            title = "Euler's Formula & Polar Representation",
            formulaDisplay = "z = a + bi = r(cos θ + i sin θ) = r · e^(iθ)   |   e^(iπ) + 1 = 0",
            description = "Expresses complex numbers in trigonometric and exponential form with modulus r = √(a² + b²) and argument θ = atan2(b, a).",
            category = MathCategory.COMPLEX_NUMBERS,
            subcategory = "Representations",
            variables = listOf("r = |z| = √(a² + b²)", "θ: Argument in radians", "i: Imaginary unit (i² = -1)"),
            example = "1 + i√3 -> r = √(1 + 3) = 2, θ = π/3 -> 2 e^(iπ/3)",
            memoryTip = "e^(iθ) rotates a point on the unit circle by angle θ counterclockwise!",
            isHighYield = true,
            searchTags = listOf("euler", "polar", "complex", "modulus", "argument")
        ),
        Formula(
            id = "cn_demoivre",
            title = "De Moivre's Theorem & n-th Roots of Unity",
            formulaDisplay = "[r(cos θ + i sin θ)]ⁿ = rⁿ(cos(nθ) + i sin(nθ))  |  wₖ = e^(i 2kπ / n)",
            description = "Power and root extraction theorem for complex numbers evenly spaced along the unit circle.",
            category = MathCategory.COMPLEX_NUMBERS,
            subcategory = "Powers & Roots",
            variables = listOf("n: Power integer", "k = 0, 1, ..., n-1 for n-th roots"),
            example = "(cos(30°) + i sin(30°))³ = cos(90°) + i sin(90°) = i",
            memoryTip = "To raise a complex number to power n, raise modulus to power n and MULTIPLY angle by n!",
            isHighYield = true,
            searchTags = listOf("demoivre", "roots of unity", "complex powers")
        ),

        // GEOMETRY
        Formula(
            id = "geom_3d_distance",
            title = "3D Distance & Sphere Equation",
            formulaDisplay = "d = √((x₂ - x₁)² + (y₂ - y₁)² + (z₂ - z₁)²)   |   (x - h)² + (y - k)² + (z - l)² = R²",
            description = "Calculates Euclidean metric between 3D coordinate points and standard spherical geometry.",
            category = MathCategory.GEOMETRY,
            subcategory = "3D Analytic Geometry",
            variables = listOf("(x₁, y₁, z₁), (x₂, y₂, z₂): Points in ℝ³", "(h, k, l): Center of sphere", "R: Radius"),
            example = "Distance from (0,0,0) to (2,3,6): d = √(4 + 9 + 36) = √49 = 7",
            memoryTip = "Just 2D Pythagoras with one extra coordinate: Δx² + Δy² + Δz².",
            isHighYield = true,
            searchTags = listOf("distance", "3d", "sphere", "coordinates")
        ),
        Formula(
            id = "geom_conics",
            title = "Conic Sections (Ellipse & Hyperbola)",
            formulaDisplay = "Ellipse: x²/a² + y²/b² = 1   |   Hyperbola: x²/a² - y²/b² = 1 (Asymptotes: y = ±(b/a)x)",
            description = "Standard canonical equations for planar conic sections with major axis a and minor axis b.",
            category = MathCategory.GEOMETRY,
            subcategory = "Conics",
            variables = listOf("a: Semi-major axis", "b: Semi-minor axis", "c = √(a² - b²) for ellipse focal distance"),
            example = "x²/25 + y²/9 = 1: a = 5, b = 3 -> Focal distance c = √(25 - 9) = 4",
            memoryTip = "Plus sign in equation -> Ellipse (focal c² = a² - b²). Minus sign -> Hyperbola (focal c² = a² + b²). Signs are opposite!",
            isHighYield = true,
            searchTags = listOf("conic", "ellipse", "hyperbola", "foci", "asymptotes")
        ),

        // PROBABILITY & STATISTICS
        Formula(
            id = "stat_bayes",
            title = "Bayes' Theorem & Conditional Probability",
            formulaDisplay = "P(A | B) = [ P(B | A) · P(A) ] / P(B)",
            description = "Calculates the posterior probability of event A given evidence B using prior and likelihood probabilities.",
            category = MathCategory.STATISTICS,
            subcategory = "Probability",
            variables = listOf("P(A | B): Posterior probability", "P(B | A): Likelihood", "P(A): Prior", "P(B): Marginal evidence"),
            example = "P(Disease) = 0.01, P(+|Disease) = 0.95, P(+|No) = 0.05 -> P(D|+) = (0.95 · 0.01) / ((0.95·0.01) + (0.05·0.99)) ≈ 0.161 (16.1%)",
            memoryTip = "Posterior = (Likelihood × Prior) ÷ Evidence. Flips condition from B given A to A given B!",
            isHighYield = true,
            searchTags = listOf("bayes", "conditional", "probability", "statistics")
        ),
        Formula(
            id = "stat_normal_distribution",
            title = "Normal (Gaussian) Distribution",
            formulaDisplay = "f(x) = (1 / (σ · √(2π))) · e^[ -(x - μ)² / (2σ²) ]   |   z = (x - μ) / σ",
            description = "Probability density function of the continuous Gaussian bell curve centered at mean μ with standard deviation σ.",
            category = MathCategory.STATISTICS,
            subcategory = "Distributions",
            variables = listOf("μ: Population mean", "σ: Standard deviation", "z: Standardized z-score"),
            example = "If μ = 100, σ = 15, x = 130: z = (130 - 100) / 15 = 2.0 (2 standard deviations above mean)",
            memoryTip = "68 - 95 - 99.7 Empirical Rule: 68% within 1σ, 95% within 2σ, 99.7% within 3σ!",
            isHighYield = true,
            searchTags = listOf("normal", "gaussian", "bell curve", "z-score", "standard deviation")
        ),
        // STD 10TH & 12TH ADDITIONS
        Formula(
            id = "std10_real_hcf_lcm",
            title = "HCF & LCM Relationship (Std 10)",
            formulaDisplay = "HCF(a, b) · LCM(a, b) = a · b",
            description = "Fundamental fundamental theorem of arithmetic property connecting the Highest Common Factor and Least Common Multiple of two positive integers.",
            category = MathCategory.ALGEBRA,
            subcategory = "Number Systems",
            variables = listOf("a, b: Positive integers"),
            example = "For a = 12, b = 18 -> HCF = 6, LCM = 36 -> 6 · 36 = 216 = 12 · 18",
            memoryTip = "Product of two numbers is always equal to the product of their HCF and LCM!",
            isHighYield = true,
            searchTags = listOf("hcf", "lcm", "real numbers", "std 10")
        ),
        Formula(
            id = "std10_mensuration_heron",
            title = "Heron's Formula & Mensuration (Std 10)",
            formulaDisplay = "Area = √[s(s - a)(s - b)(s - c)]   |   s = (a + b + c) / 2",
            description = "Calculates the area of any triangle given its three side lengths without needing height or angles.",
            category = MathCategory.GEOMETRY,
            subcategory = "Mensuration",
            variables = listOf("s: Semi-perimeter", "a, b, c: Triangle side lengths"),
            example = "Sides 3, 4, 5 -> s = (3+4+5)/2 = 6 -> Area = √(6 · 3 · 2 · 1) = √36 = 6",
            memoryTip = "Find semi-perimeter s first, then take square root of s times (s - side) for each side!",
            isHighYield = true,
            searchTags = listOf("heron", "triangle area", "mensuration", "std 10")
        ),
        Formula(
            id = "std10_statistics_median",
            title = "Median & Mode for Grouped Data (Std 10)",
            formulaDisplay = "Median = L + [ ((n/2 - cf) / f) ] · h   |   Mode = L + [ (f₁ - f₀) / (2f₁ - f₀ - f₂) ] · h",
            description = "Calculates central tendency estimates for continuous grouped frequency distribution tables in statistics.",
            category = MathCategory.STATISTICS,
            subcategory = "Statistics",
            variables = listOf("L: Lower limit of median/modal class", "n: Total frequency", "cf: Cumulative frequency before median class", "f: Frequency of median class", "h: Class width"),
            example = "Standard grouped frequency evaluation for class 10 board examinations.",
            memoryTip = "Memorize the lower limit L, class size h, and frequency fractions for board viva and exams!",
            isHighYield = true,
            searchTags = listOf("median", "mode", "grouped data", "statistics", "std 10")
        ),
        Formula(
            id = "std12_itf_properties",
            title = "Inverse Trigonometric Properties (Std 12)",
            formulaDisplay = "sin⁻¹x + cos⁻¹x = π/2   |   tan⁻¹x + cot⁻¹x = π/2   |   tan⁻¹x + tan⁻¹y = tan⁻¹((x + y)/(1 - xy))",
            description = "Key domain/range and summation identities for inverse trigonometric functions in calculus.",
            category = MathCategory.TRIGONOMETRY,
            subcategory = "Inverse Trig",
            variables = listOf("x, y: Real number arguments satisfying domain constraints"),
            example = "tan⁻¹(1/2) + tan⁻¹(1/3) = tan⁻¹((1/2 + 1/3) / (1 - 1/6)) = tan⁻¹(1) = π/4",
            memoryTip = "Complementary inverse ratios (sin & cos, tan & cot, sec & csc) always sum to π/2!",
            isHighYield = true,
            searchTags = listOf("inverse trigonometry", "std 12", "calculus", "identities")
        ),
        Formula(
            id = "std12_vector_cross",
            title = "Vector Cross Product & Area (Std 12)",
            formulaDisplay = "a × b = |a||b|sinθ · n̂   |   Triangle Area = ½ |a × b|",
            description = "Vector multiplication producing a perpendicular vector, used to find torque, normals, and triangle/parallelogram areas.",
            category = MathCategory.GEOMETRY,
            subcategory = "Vectors & 3D",
            variables = listOf("a, b: 3D vectors", "θ: Angle between vectors", "n̂: Unit normal vector"),
            example = "î × ĵ = k̂,  ĵ × k̂ = î,  k̂ × î = ĵ",
            memoryTip = "Cyclic order for cross product: i × j = k, j × k = i, k × i = j. Reversing order gives negative sign!",
            isHighYield = true,
            searchTags = listOf("cross product", "vectors", "area", "std 12", "3d geometry")
        ),
        Formula(
            id = "std12_standard_integrals",
            title = "Standard Calculus Integrals (Std 12)",
            formulaDisplay = "∫ dx/(x² - a²) = (1/2a)ln|(x-a)/(x+a)|   |   ∫ dx/√(x² + a²) = ln|x + √(x² + a²)|",
            description = "Standard integral formulas frequently applied in board examinations for algebraic rational and irrational functions.",
            category = MathCategory.CALCULUS,
            subcategory = "Standard Integrals",
            variables = listOf("x: Variable", "a: Constant positive number"),
            example = "∫ dx/(x² - 4) = (1/4) ln|(x-2)/(x+2)| + C",
            memoryTip = "Notice the 1/(2a) factor in reciprocal square difference formulas!",
            isHighYield = true,
            searchTags = listOf("integrals", "calculus", "std 12", "standard formulas")
        )
    )

    val SOLVED_EQUATIONS: List<SolvedEquation> = listOf(
        SolvedEquation(
            id = "solved_quad_1",
            title = "Quadratic with Distinct Real Roots",
            topic = "Algebra",
            problem = "Solve for x:  x² - 5x + 6 = 0",
            finalAnswer = "x = 3  or  x = 2",
            keyTakeaway = "Always check if the equation factors easily by finding two numbers that multiply to c and add to b before using the full quadratic formula.",
            steps = listOf(
                SolutionStep(1, "Identify coefficients for ax² + bx + c = 0", "a = 1,  b = -5,  c = 6"),
                SolutionStep(2, "Compute the discriminant Δ = b² - 4ac", "Δ = (-5)² - 4(1)(6) = 25 - 24 = 1"),
                SolutionStep(3, "Apply the quadratic formula x = (-b ± √Δ) / (2a)", "x = (-(-5) ± √1) / (2 · 1) = (5 ± 1) / 2"),
                SolutionStep(4, "Split into two branches for plus and minus", "x₁ = (5 + 1)/2 = 3   and   x₂ = (5 - 1)/2 = 2")
            )
        ),
        SolvedEquation(
            id = "solved_quad_complex",
            title = "Quadratic with Complex Roots",
            topic = "Algebra",
            problem = "Solve for x:  x² + 2x + 5 = 0",
            finalAnswer = "x = -1 ± 2i",
            keyTakeaway = "When the discriminant Δ < 0, roots always occur in complex conjugate pairs: -b/(2a) ± i(√|Δ|)/(2a).",
            steps = listOf(
                SolutionStep(1, "Identify quadratic coefficients", "a = 1,  b = 2,  c = 5"),
                SolutionStep(2, "Calculate discriminant Δ = b² - 4ac", "Δ = 2² - 4(1)(5) = 4 - 20 = -16"),
                SolutionStep(3, "Evaluate square root of negative discriminant", "√(-16) = √16 · √(-1) = 4i"),
                SolutionStep(4, "Substitute into quadratic formula", "x = (-2 ± 4i) / (2 · 1) = -1 ± 2i")
            )
        ),
        SolvedEquation(
            id = "solved_linear_system",
            title = "2×2 Linear System (Cramer's Rule)",
            topic = "Linear Algebra",
            problem = "Solve the system:\n3x + 2y = 12\nx - y = 1",
            finalAnswer = "x = 2.8,  y = 1.8   (or x = 14/5, y = 9/5)",
            keyTakeaway = "Cramer's rule is fast and reliable for 2x2 systems: x = Dx / D and y = Dy / D, provided determinant D ≠ 0.",
            steps = listOf(
                SolutionStep(1, "Compute the coefficient matrix determinant D", "D = det[3  2 ; 1 -1] = 3(-1) - 2(1) = -3 - 2 = -5"),
                SolutionStep(2, "Construct Dx by replacing column 1 with constants [12; 1]", "Dx = det[12  2 ; 1 -1] = 12(-1) - 2(1) = -12 - 2 = -14"),
                SolutionStep(3, "Construct Dy by replacing column 2 with constants [12; 1]", "Dy = det[3  12 ; 1  1] = 3(1) - 12(1) = 3 - 12 = -9"),
                SolutionStep(4, "Solve for x and y by dividing determinants", "x = Dx / D = -14 / -5 = 14/5 = 2.8\ny = Dy / D = -9 / -5 = 9/5 = 1.8")
            )
        ),
        SolvedEquation(
            id = "solved_derivative_prod_chain",
            title = "Derivative using Product & Chain Rule",
            topic = "Calculus",
            problem = "Find dy/dx for:  y = x² · sin(3x)",
            finalAnswer = "dy/dx = 2x · sin(3x) + 3x² · cos(3x)",
            keyTakeaway = "Differentiate step by step: (u·v)' = u'·v + u·v', and don't forget the inner derivative 3 from chain rule on sin(3x).",
            steps = listOf(
                SolutionStep(1, "Decompose into factor functions u and v", "u(x) = x²   and   v(x) = sin(3x)"),
                SolutionStep(2, "Differentiate u(x)", "u'(x) = 2x"),
                SolutionStep(3, "Differentiate v(x) using Chain Rule: d/dx[sin(g(x))] = cos(g(x)) · g'(x)", "v'(x) = cos(3x) · 3 = 3 cos(3x)"),
                SolutionStep(4, "Combine using Product Rule: y' = u'v + uv'", "y' = (2x)(sin(3x)) + (x²)(3 cos(3x)) = 2x sin(3x) + 3x² cos(3x)")
            )
        ),
        SolvedEquation(
            id = "solved_integration_parts",
            title = "Definite / Indefinite Integration by Parts",
            topic = "Calculus",
            problem = "Evaluate the integral:  ∫ x · eˣ dx",
            finalAnswer = "x · eˣ - eˣ + C = eˣ(x - 1) + C",
            keyTakeaway = "Select u using LIATE. Since algebraic (x) precedes exponential (eˣ), choose u = x so du becomes a simple constant derivative.",
            steps = listOf(
                SolutionStep(1, "Assign parts u and dv according to LIATE rule", "u = x   ->   du = dx\ndv = eˣ dx   ->   v = ∫ eˣ dx = eˣ"),
                SolutionStep(2, "Apply Integration by Parts formula: ∫ u dv = u·v - ∫ v du", "∫ x eˣ dx = x · eˣ - ∫ eˣ dx"),
                SolutionStep(3, "Evaluate the remaining simple integral ∫ eˣ dx", "∫ eˣ dx = eˣ"),
                SolutionStep(4, "Combine terms and factor out eˣ", "x eˣ - eˣ + C = eˣ(x - 1) + C")
            )
        ),
        SolvedEquation(
            id = "solved_radical_rationalization",
            title = "Radical Rationalization with Conjugate",
            topic = "Squares & Roots",
            problem = "Simplify and eliminate radicals from denominator:  6 / (√5 - √2)",
            finalAnswer = "2(√5 + √2) = 2√5 + 2√2",
            keyTakeaway = "Multiplying (√a - √b) by conjugate (√a + √b) yields the difference of squares a - b, which is purely rational.",
            steps = listOf(
                SolutionStep(1, "Identify conjugate of denominator (√5 - √2)", "Conjugate = (√5 + √2)"),
                SolutionStep(2, "Multiply numerator and denominator by conjugate", "[ 6 · (√5 + √2) ] / [ (√5 - √2)(√5 + √2) ]"),
                SolutionStep(3, "Expand difference of squares in denominator: (√5)² - (√2)²", "5 - 2 = 3"),
                SolutionStep(4, "Simplify fraction 6 / 3", "(6 / 3) · (√5 + √2) = 2(√5 + √2) = 2√5 + 2√2")
            )
        ),
        SolvedEquation(
            id = "solved_matrix_determinant_3x3",
            title = "Determinant of 3×3 Matrix",
            topic = "Linear Algebra",
            problem = "Compute determinant of:\n[ 1  2  3 ;\n  0  4  5 ;\n  1  0  6 ]",
            finalAnswer = "det(A) = 22",
            keyTakeaway = "Expand along the row or column containing the most zeros to minimize 2x2 minor calculations.",
            steps = listOf(
                SolutionStep(1, "Write cofactor expansion along row 1: a₁₁M₁₁ - a₁₂M₁₂ + a₁₃M₁₃", "det(A) = 1 · det[4 5; 0 6] - 2 · det[0 5; 1 6] + 3 · det[0 4; 1 0]"),
                SolutionStep(2, "Evaluate first 2x2 minor: det[4 5; 0 6]", "4(6) - 5(0) = 24"),
                SolutionStep(3, "Evaluate second 2x2 minor: det[0 5; 1 6]", "0(6) - 5(1) = -5"),
                SolutionStep(4, "Evaluate third 2x2 minor: det[0 4; 1 0]", "0(0) - 4(1) = -4"),
                SolutionStep(5, "Combine terms with alternating signs", "1(24) - 2(-5) + 3(-4) = 24 + 10 - 12 = 22")
            )
        )
    )
}
