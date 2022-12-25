package com.san68bot.learningsplines.math

import com.san68bot.learningsplines.graphics.point.DataPoint

object SplineMath {
    /**
     * Linear Interpolation as a function of t
     */
    fun lerp(t: Double, start: Double, end: Double) = (1.0 - t) * start + t * end

    fun lerp(t: Double, start: DataPoint, end: DataPoint) = DataPoint(lerp(t, start.x, end.x), lerp(t, start.y, end.y))
}