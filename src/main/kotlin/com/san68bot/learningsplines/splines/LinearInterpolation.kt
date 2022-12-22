package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.graphics.*
import com.san68bot.learningsplines.math.*
import javafx.scene.Group
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle

data class LinearEvaluation(val p1: Point, val p2: Point) {
    fun x(t: Double) = (1.0 - t) * p1.x + t * p2.x
    fun y(t: Double) = (1.0 - t) * p1.y + t * p2.y
    operator fun get(t: Double) = Point(x(t), y(t))
}

class LinearInterpolation(
    pane: Pane,
    private val points: Array<DynamicPoint>
) {
    private val pathGroup = Group()

    init {
        pane.children.addAll(pathGroup)
        pane.children.addAll(points)
        refresh()
    }

    fun refresh() {
        pathGroup.children.clear()
        (0 until points.size - 1).forEach { i ->
            LinearEvaluation(Point(points[i].x, points[i].y), Point(points[i+1].x, points[i+1].y)).apply {
                (0.0..1.0 step 0.01).forEach { t ->
                    pathGroup.children.add(Circle(x(t), y(t), 1.5).apply { fill = BetterColors.purple })
                }
            }
        }
    }
}