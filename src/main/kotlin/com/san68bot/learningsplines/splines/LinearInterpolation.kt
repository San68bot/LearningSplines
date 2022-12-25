package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.*
import com.san68bot.learningsplines.math.*
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import kotlin.math.*

class LinearInterpolation(
    pane: Pane,
    private val points: Array<DynamicPoint>
): Interpolation(pane, points) {
    init {
        refresh()
    }

    data class LinearEvaluation(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {
        val distance get() = hypot((x2 - x1), (y2 - y1))
        fun x(t: Double) = (1.0 - t) * x1 + t * x2
        fun y(t: Double) = (1.0 - t) * y1 + t * y2
    }

    override fun refresh() {
        arc_length = 0.0
        pathGroup.children.clear()
        (0 until points.size - 1).forEach { i ->
            LinearEvaluation(points[i].x, points[i].y, points[i+1].x, points[i+1].y).apply {
                arc_length += distance
                (0.0..1.0 step 0.01).forEach { t ->
                    pathGroup.children.add(Circle(x(t), y(t), 1.5).apply { fill = BetterColors.purple })
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