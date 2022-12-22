package com.san68bot.learningsplines.math

data class Point(var x: Double, var y: Double) {
    operator fun plus(p: Point) =
        Point(x + p.x, y + p.y)

    operator fun minus(p: Point) =
        Point(x - p.x, y - p.y)

    operator fun times(p: Point) =
        Point(x * p.x, y * p.y)

    operator fun div(p: Point) =
        Point(x / p.x, y / p.y)

    operator fun times(s: Double) =
        Point(x * s, y * s)

    operator fun div(s: Double) =
        Point(x / s, y / s)

    operator fun unaryMinus() =
        Point(-x, -y)

    override fun toString(): String = "($x, $y)"
}