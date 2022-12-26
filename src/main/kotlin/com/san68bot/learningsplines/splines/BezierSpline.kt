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
                val eval = bernstein(t, cp_list[i], start, cp_list[i+1].t1, cp_list[i+1])
                cp_list[i+1].t1.setColor(cp_list[i].color)
                pathGroup.children.add(Circle(eval.x, eval.y, 1.5).apply { fill = cp_list[i].color })
                arc_length += eval distance prev_eval
                prev_eval = eval
            }
        }



        /*(0 until 1).forEach { i ->
            (0.0..1.0 step 0.01).forEach { t ->
                val eval = bernstein(t, m_list[i], m_list[i + 1], m_list[i + 2], m_list[i + 3])
                pathGroup.children.add(Circle(eval.x, eval.y, 1.5).apply { fill = BetterColors.purple })
                arc_length += eval distance prev_eval
                prev_eval = eval
            }
        }*/
        log()
    }

    /**
     * Bernstein Algorithm
     * Expanded out form of DeCasteljau Algorithm, combined all the lerps into one big equation
     * Essentially a vector of each component scaled by the point
     */
    private fun bernstein(t: Double, p0: Double, p1: Double, p2: Double, p3: Double): Double {
        return p0 * (-t.pow(3) + 3*t.pow(2) - 3*t + 1) +
                p1 * (3*t.pow(3) - 6*t.pow(2) + 3*t) +
                p2 * (-3*t.pow(3) + 3*t.pow(2)) +
                p3 * (t.pow(3))
    }
    private fun bernstein(t: Double, p0: Point, p1: Point, p2: Point, p3: Point): Point {
        return Point(
            bernstein(t, p0.x, p1.x, p2.x, p3.x),
            bernstein(t, p0.y, p1.y, p2.y, p3.y)
        )
    }

    override fun log() {
        telemetryManager
            .add("Arc Length", "Arc Length: ${arc_length round 3}")
            .update()
    }
}