package com.san68bot.learningsplines.math

import com.san68bot.learningsplines.graphics.points.Point

object SplineMath {
    /**
     * Linear Interpolation as a function of t
     */
    fun lerp(t: Double, start: Double, end: Double) = (1.0 - t) * start + t * end
    fun lerp(t: Double, start: Point, end: Point) = (1.0 - t) * start + t * end
}