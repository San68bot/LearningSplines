package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.DynamicPoint
import com.san68bot.learningsplines.math.Point
import com.san68bot.learningsplines.math.SplineMath.lerp
import com.san68bot.learningsplines.math.round
import com.san68bot.learningsplines.math.step
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle

class BezierInterpolation(
    pane: Pane,
    private val points: Array<DynamicPoint>
): Interpolation(pane, points) {
    init {
        refresh()
    }

    override fun refresh() {
        super.refresh()
        (0.0..1.0 step 0.01).forEach { t ->
            val lerp = recursive_lerp(t, ArrayList(points.map { Point(it.x, it.y) }))
            pathGroup.children.add(Circle(lerp.x, lerp.y, 1.5).apply { fill = BetterColors.purple })
        }
        log()
    }

    private fun recursive_lerp(t: Double, points: ArrayList<Point>): Point {
        return when (points.size) {
            2 -> lerp(t, points[0], points[1])
            else -> recursive_lerp(t, ArrayList((0 until points.size - 1).map { i ->
                lerp(t, points[i], points[i+1])
            }))
        }
    }

    override fun log() {
        telemetryManager
            .add("Arc Length", "Arc Length: ${arc_length round 3}")
            .update()
    }
}