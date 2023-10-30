package com.san68bot.learningsplines.math

import org.ejml.simple.SimpleMatrix

fun main() {
    val x = SimpleMatrix(
        4, 4, true,
        doubleArrayOf(
            1.0, 1.0, 1.0, 1.0,
            8.0, 4.0, 2.0, 1.0,
            27.0, 9.0, 3.0, 1.0,
            0.0, 0.0, 0.0, 1.0
        )
    )
    val y = SimpleMatrix(
        4, 1, true,
        doubleArrayOf(
            6.0,
            22.0,
            70.0,
            4.0
        )
    )
    val solved = x.pseudoInverse().mult(y)
    solved.print()
}