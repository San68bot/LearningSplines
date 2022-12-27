package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.points.ControlPoint
import com.san68bot.learningsplines.graphics.points.Point
import com.san68bot.learningsplines.math.round
import com.san68bot.learningsplines.math.step
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import kotlin.math.pow

/**
 * No local control
 * Doesn't pass through most points
 * Expensive to calculate
 */
class BezierSpline(
    pane: Pane,
    private val points: ArrayList<Point>,
): Interpolation(pane, points) {
    init {
        refresh()
    }

    override fun refresh() {
        var prev_eval = points[0]
        super.refresh()
        val cp_list = points.map { it as ControlPoint }
        (0 until cp_list.size - 1).forEach { i ->
            (0.0..1.0 step 0.01).forEach { t ->
                val start = if (cp_list[i].t2Active) cp_list[i].t2 else cp_list[i].t1
                val eval = Bezier.bernstein(t, cp_list[i], start, cp_list[i+1].t1, cp_list[i+1])
                pathGroup.children.add(Circle(eval.x, eval.y, 1.5).apply { fill = BetterColors.gradient(cp_list[i].color, cp_list[i+1].color, t) })
                arc_length += eval distance prev_eval
                prev_eval = eval
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