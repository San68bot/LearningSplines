package com.san68bot.learningsplines.graphics.point

import kotlin.math.*

interface Point {
    val x: Double
    val y: Double

    operator fun plus(p: Point) =
        DataPoint(x + p.x, y + p.y)

    operator fun minus(p: Point) =
        DataPoint(x - p.x, y - p.y)

    operator fun times(p: Point) =
        DataPoint(x * p.x, y * p.y)

    operator fun div(p: Point) =
        DataPoint(x / p.x, y / p.y)

    operator fun times(s: Double) =
        DataPoint(x * s, y * s)

    operator fun div(s: Double) =
        DataPoint(x / s, y / s)

    operator fun unaryMinus() =
        DataPoint(-x, -y)

    infix fun distance(p: Point) =
        sqrt((x - p.x).pow(2) + (y - p.y).pow(2))
}

data class DataPoint(
    override var x: Double,
    override var y: Double
): Point