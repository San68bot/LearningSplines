package com.san68bot.learningsplines.math

import kotlin.math.*

data class DataPoint(var x: Double, var y: Double) {
    operator fun plus(p: DataPoint) =
        DataPoint(x + p.x, y + p.y)

    operator fun minus(p: DataPoint) =
        DataPoint(x - p.x, y - p.y)

    operator fun times(p: DataPoint) =
        DataPoint(x * p.x, y * p.y)

    operator fun div(p: DataPoint) =
        DataPoint(x / p.x, y / p.y)

    operator fun times(s: Double) =
        DataPoint(x * s, y * s)

    operator fun div(s: Double) =
        DataPoint(x / s, y / s)

    operator fun unaryMinus() =
        DataPoint(-x, -y)

    infix fun distance(p: DataPoint) =
        sqrt((x - p.x).pow(2) + (y - p.y).pow(2))

    override fun toString(): String = "($x, $y)"
}