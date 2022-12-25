package com.san68bot.learningsplines.math

object SplineMath {
    /**
     * Linear Interpolation as a function of t
     */
    fun lerp(t: Double, start: Double, end: Double) = (1.0 - t) * start + t * end

    fun lerp(t: Double, start: Point, end: Point) = Point(lerp(t, start.x, end.x), lerp(t, start.y, end.y))
}