package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.*
import com.san68bot.learningsplines.math.*
import javafx.scene.Group
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import kotlin.math.*

data class LinearEvaluation(val x1: Double, val y1: Double, val x2: Double, val y2: Double) {
    val distance get() = hypot((x2 - x1), (y2 - y1))
    fun x(t: Double) = (1.0 - t) * x1 + t * x2
    fun y(t: Double) = (1.0 - t) * y1 + t * y2
    operator fun get(t: Double) = Point(x(t), y(t))
}

class LinearInterpolation(
    pane: Pane,
    private val points: Array<DynamicPoint>
) {
    private val pathGroup = Group()
    private var totalLength = 0.0

    init {
        Globals.updateBlock = { refresh() }
        pane.children.addAll(pathGroup)
        pane.children.addAll(points)
        refresh()
    }

    fun refresh() {
        totalLength = 0.0
        pathGroup.children.clear()
        (0 until points.size - 1).forEach { i ->
            LinearEvaluation(points[i].x, points[i].y, points[i+1].x, points[i+1].y).apply {
                totalLength += distance
                (0.0..1.0 step 0.01).forEach { t ->
                    pathGroup.children.add(Circle(x(t), y(t), 1.5).apply { fill = BetterColors.purple })
                }
            }
        }
        telemetryManager.add("Total Length", "Total Length: ${totalLength round 3}").update()
    }
}