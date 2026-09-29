package com.example.math

import kotlin.math.*

class ScientificEvaluator(private val isDegreeMode: Boolean = true) {

    fun evaluate(expression: String): Result<Double> {
        return try {
            val sanitized = sanitize(expression)
            if (sanitized.isEmpty()) return Result.failure(IllegalArgumentException("Empty expression"))
            val tokens = tokenize(sanitized)
            val rpn = toRpn(tokens)
            val result = evalRpn(rpn)
            if (result.isNaN()) Result.failure(ArithmeticException("Undefined (NaN)"))
            else if (result.isInfinite()) Result.failure(ArithmeticException("Overflow/Division by zero"))
            else Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun sanitize(expr: String): String {
        return expr
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("π", "PI")
            .replace("√", "sqrt")
            .replace("∛", "cbrt")
            .replace(" ", "")
    }

    private enum class TokenType {
        NUMBER, OPERATOR, FUNCTION, LPAREN, RPAREN, COMMA
    }

    private data class Token(val type: TokenType, val value: String)

    private fun tokenize(expr: String): List<Token> {
        val tokens = mutableListOf<Token>()
        var i = 0
        val n = expr.length

        while (i < n) {
            val c = expr[i]

            when {
                c.isDigit() || c == '.' -> {
                    val sb = StringBuilder()
                    while (i < n && (expr[i].isDigit() || expr[i] == '.')) {
                        sb.append(expr[i])
                        i++
                    }
                    tokens.add(Token(TokenType.NUMBER, sb.toString()))
                    continue
                }
                c == '(' -> {
                    // Implicit multiplication check (e.g., 2(3) -> 2*(3))
                    if (tokens.isNotEmpty()) {
                        val prev = tokens.last()
                        if (prev.type == TokenType.NUMBER || prev.type == TokenType.RPAREN) {
                            tokens.add(Token(TokenType.OPERATOR, "*"))
                        }
                    }
                    tokens.add(Token(TokenType.LPAREN, "("))
                    i++
                }
                c == ')' -> {
                    tokens.add(Token(TokenType.RPAREN, ")"))
                    i++
                }
                c == ',' -> {
                    tokens.add(Token(TokenType.COMMA, ","))
                    i++
                }
                c in "+-*/^%" -> {
                    // Check if unary minus
                    if (c == '-') {
                        val isUnary = tokens.isEmpty() ||
                                tokens.last().type == TokenType.OPERATOR ||
                                tokens.last().type == TokenType.LPAREN
                        if (isUnary) {
                            // Unary minus treated as 0 - x or negated number
                            tokens.add(Token(TokenType.NUMBER, "0"))
                            tokens.add(Token(TokenType.OPERATOR, "-"))
                            i++
                            continue
                        }
                    }
                    tokens.add(Token(TokenType.OPERATOR, c.toString()))
                    i++
                }
                c == '!' -> {
                    tokens.add(Token(TokenType.OPERATOR, "!"))
                    i++
                }
                c.isLetter() -> {
                    val sb = StringBuilder()
                    while (i < n && (expr[i].isLetter() || expr[i].isDigit())) {
                        sb.append(expr[i])
                        i++
                    }
                    val name = sb.toString()
                    when (name.uppercase()) {
                        "PI" -> {
                            if (tokens.isNotEmpty() && (tokens.last().type == TokenType.NUMBER || tokens.last().type == TokenType.RPAREN)) {
                                tokens.add(Token(TokenType.OPERATOR, "*"))
                            }
                            tokens.add(Token(TokenType.NUMBER, Math.PI.toString()))
                        }
                        "E" -> {
                            if (tokens.isNotEmpty() && (tokens.last().type == TokenType.NUMBER || tokens.last().type == TokenType.RPAREN)) {
                                tokens.add(Token(TokenType.OPERATOR, "*"))
                            }
                            tokens.add(Token(TokenType.NUMBER, Math.E.toString()))
                        }
                        else -> {
                            // Implicit multiplication for functions if preceded by number
                            if (tokens.isNotEmpty() && (tokens.last().type == TokenType.NUMBER || tokens.last().type == TokenType.RPAREN)) {
                                tokens.add(Token(TokenType.OPERATOR, "*"))
                            }
                            tokens.add(Token(TokenType.FUNCTION, name.lowercase()))
                        }
                    }
                    continue
                }
                else -> {
                    i++ // Ignore unrecognized character
                }
            }
        }
        return tokens
    }

    private fun precedence(op: String): Int = when (op) {
        "+", "-" -> 1
        "*", "/", "%" -> 2
        "^" -> 3
        "!" -> 4
        else -> 0
    }

    private fun isRightAssociative(op: String): Boolean = op == "^"

    private fun toRpn(tokens: List<Token>): List<Token> {
        val output = mutableListOf<Token>()
        val stack = ArrayDeque<Token>()

        for (token in tokens) {
            when (token.type) {
                TokenType.NUMBER -> output.add(token)
                TokenType.FUNCTION -> stack.addFirst(token)
                TokenType.OPERATOR -> {
                    while (stack.isNotEmpty() && stack.first().type == TokenType.OPERATOR) {
                        val top = stack.first()
                        val p1 = precedence(token.value)
                        val p2 = precedence(top.value)
                        if ((!isRightAssociative(token.value) && p1 <= p2) || (isRightAssociative(token.value) && p1 < p2)) {
                            output.add(stack.removeFirst())
                        } else {
                            break
                        }
                    }
                    stack.addFirst(token)
                }
                TokenType.LPAREN -> stack.addFirst(token)
                TokenType.RPAREN -> {
                    while (stack.isNotEmpty() && stack.first().type != TokenType.LPAREN) {
                        output.add(stack.removeFirst())
                    }
                    if (stack.isNotEmpty() && stack.first().type == TokenType.LPAREN) {
                        stack.removeFirst() // Remove '('
                    }
                    if (stack.isNotEmpty() && stack.first().type == TokenType.FUNCTION) {
                        output.add(stack.removeFirst())
                    }
                }
                TokenType.COMMA -> {
                    while (stack.isNotEmpty() && stack.first().type != TokenType.LPAREN) {
                        output.add(stack.removeFirst())
                    }
                }
            }
        }

        while (stack.isNotEmpty()) {
            output.add(stack.removeFirst())
        }

        return output
    }

    private fun evalRpn(rpn: List<Token>): Double {
        val evalStack = ArrayDeque<Double>()

        for (token in rpn) {
            when (token.type) {
                TokenType.NUMBER -> evalStack.addFirst(token.value.toDouble())
                TokenType.OPERATOR -> {
                    if (token.value == "!") {
                        val a = evalStack.removeFirst()
                        evalStack.addFirst(factorial(a))
                    } else {
                        val b = evalStack.removeFirst()
                        val a = evalStack.removeFirst()
                        val res = when (token.value) {
                            "+" -> a + b
                            "-" -> a - b
                            "*" -> a * b
                            "/" -> {
                                if (b == 0.0) throw ArithmeticException("Divide by zero")
                                a / b
                            }
                            "%" -> a % b
                            "^" -> a.pow(b)
                            else -> throw IllegalArgumentException("Unknown operator: ${token.value}")
                        }
                        evalStack.addFirst(res)
                    }
                }
                TokenType.FUNCTION -> {
                    val a = evalStack.removeFirst()
                    val res = when (token.value) {
                        "sin" -> sin(if (isDegreeMode) Math.toRadians(a) else a)
                        "cos" -> cos(if (isDegreeMode) Math.toRadians(a) else a)
                        "tan" -> {
                            val rad = if (isDegreeMode) Math.toRadians(a) else a
                            tan(rad)
                        }
                        "asin" -> {
                            val r = asin(a)
                            if (isDegreeMode) Math.toDegrees(r) else r
                        }
                        "acos" -> {
                            val r = acos(a)
                            if (isDegreeMode) Math.toDegrees(r) else r
                        }
                        "atan" -> {
                            val r = atan(a)
                            if (isDegreeMode) Math.toDegrees(r) else r
                        }
                        "sqrt" -> {
                            if (a < 0) throw ArithmeticException("Square root of negative number")
                            sqrt(a)
                        }
                        "cbrt" -> cbrt(a)
                        "log", "log10" -> {
                            if (a <= 0) throw ArithmeticException("Log of non-positive number")
                            log10(a)
                        }
                        "ln" -> {
                            if (a <= 0) throw ArithmeticException("Ln of non-positive number")
                            ln(a)
                        }
                        "abs" -> abs(a)
                        "fact" -> factorial(a)
                        "inv" -> 1.0 / a
                        "sq" -> a * a
                        "cb" -> a * a * a
                        else -> throw IllegalArgumentException("Unknown function: ${token.value}")
                    }
                    evalStack.addFirst(res)
                }
                else -> {}
            }
        }

        return evalStack.firstOrNull() ?: 0.0
    }

    private fun factorial(n: Double): Double {
        if (n < 0 || n != floor(n)) throw IllegalArgumentException("Factorial requires non-negative integer")
        if (n > 170) return Double.POSITIVE_INFINITY
        var result = 1.0
        for (i in 2..n.toInt()) {
            result *= i
        }
        return result
    }

    companion object {
        fun formatResult(value: Double): String {
            if (value.isNaN()) return "NaN"
            if (value.isInfinite()) return if (value > 0) "∞" else "-∞"
            // If it's a very close integer
            if (abs(value - round(value)) < 1e-10) {
                return round(value).toLong().toString()
            }
            // Scientific notation for very large/small numbers
            if (abs(value) >= 1e10 || (abs(value) > 0 && abs(value) < 1e-4)) {
                return String.format("%.6e", value).replace("e+0", "e").replace("e+", "e")
            }
            // Normal decimal formatting trimming trailing zeros
            val str = String.format("%.8f", value)
            return str.trimEnd('0').trimEnd('.')
        }
    }
}
