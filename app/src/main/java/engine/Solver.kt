package com.yourpackage.engine

object Solver {

	fun solveLinear(equation: Equation): Int? {
		// expects form ax = b
		if (equation.left.coefficient == 0) return null

		val a = equation.left.coefficient
		val b = equation.right.constant

		return b / a
	}
}
