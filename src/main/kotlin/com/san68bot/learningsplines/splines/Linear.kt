package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.*
import com.san68bot.learningsplines.graphics.points.DynamicPoint
import com.san68bot.learningsplines.graphics.points.Point
import com.san68bot.learningsplines.math.*
import com.san68bot.learningsplines.math.SplineMath.lerp
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import kotlin.math.*

class Linear(
    pane: Pane,
    private val points: ArrayList<Point>
): Interpolation(pane, points) {
    init {
        refresh()
    }

    data class LinearEvaluation(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {
        val distance get() = hypot((x2 - x1), (y2 - y1))
        fun x(t: Double) = lerp(t, x1, x2)
        fun y(t: Double) = lerp(t, y1, y2)
    }

    override fun refresh() {
        super.refresh()
        (0 until points.size - 1).forEach { i ->
            if (!points[i].x.isNaN() && !points[i].y.isNaN()) {
                LinearEvaluation(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y).apply {
                    arc_length += distance
                    (0.0..1.0 step 0.01).forEach { t ->
                        pathGroup.children.add(Circle(x(t), y(t), 1.5).apply { fill = BetterColors.purple })
                    }
                }
            }
        }
        log()
    }

    override fun log() {
        telemetryManager
            .add("Arc Length", "Arc Length: ${arc_length round 3}")
            .update()
    }
}