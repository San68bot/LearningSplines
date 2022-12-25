package com.san68bot.learningsplines.math

import com.san68bot.learningsplines.graphics.points.DynamicPoint

object SplineMath {
    /**
     * Linear Interpolation as a function of t
     */
    fun lerp(t: Double, start: Double, end: Double) = (1.0 - t) * start + t * end

    fun lerp(t: Double, start: DynamicPoint, end: DynamicPoint) = DynamicPoint(lerp(t, start.x, end.x), lerp(t, start.y, end.y))
}