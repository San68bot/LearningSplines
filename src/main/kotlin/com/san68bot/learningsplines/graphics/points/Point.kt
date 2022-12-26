package com.san68bot.learningsplines.graphics.points

import com.san68bot.learningsplines.math.angleWrap
import com.san68bot.learningsplines.math.round
import com.san68bot.learningsplines.math.toDegrees
import com.san68bot.learningsplines.math.unitCircleArctan
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Shape
import kotlin.math.*

open class Point(
    open var x: Double, open var y: Double, r: Double
): Circle(x, y, r) {
    constructor(x: Double, y: Double):
        this(x, y, Double.NaN)

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

    infix fun distance(p: Point) =
        sqrt((x - p.x).pow(2) + (y - p.y).pow(2))

    infix fun angle(p: Point) =
        angleWrap(180.0 - unitCircleArctan(this, p))

    override fun toString() = "(${x round 3}, ${y round 3})"

    open var graphics = listOf<Shape?>()
}