package com.yourpackage.engine

// Represents left = right
data class Equation(
    var left: Expression,
    var right: Expression
) {

    fun moveConstantFromLeftToRight() {
        // subtract constant from left
        val constantToMove = Expression(0, left.constant)

        left = left.subtract(constantToMove)
        right = right.subtract(constantToMove)
    }

    override fun toString(): String {
        return "${left.toString()} = ${right.toString()}"
    }
}
