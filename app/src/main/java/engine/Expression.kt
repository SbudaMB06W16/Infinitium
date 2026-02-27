package com.yourpackage.engine

// Represents a very basic algebraic expression: ax + b
data class Expression(
    var coefficient: Int = 0,   // coefficient of x
    var constant: Int = 0       // constant term
) {

    // Adds another expression to this one
    fun add(other: Expression): Expression {
        return Expression(
            coefficient + other.coefficient,
            constant + other.constant
        )
    }

    // Subtracts another expression
    fun subtract(other: Expression): Expression {
        return Expression(
            coefficient - other.coefficient,
            constant - other.constant
        )
    }

    override fun toString(): String {
        val xPart = when (coefficient) {
            0 -> ""
            1 -> "x "
            -1 -> "-x "
            else -> "${coefficient}x "
        }

        val constPart = when {
            constant > 0 && xPart.isNotEmpty() -> " + $constant"
            constant < 0 -> " - ${-constant}"
            constant > 0 -> "$constant"
            else -> ""
        }

        return (xPart + constPart).ifEmpty { "0" }
    }
}
