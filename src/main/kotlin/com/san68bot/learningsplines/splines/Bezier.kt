package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.points.Point
import com.san68bot.learningsplines.math.SplineMath.lerp
import com.san68bot.learningsplines.math.round
import com.san68bot.learningsplines.math.step
import javafx.scene.layout.Pane
import javafx.scene.shape.Circle
import kotlin.math.pow

class Bezier(
    pane: Pane,
    private val points: ArrayList<Point>,
    private val calculationMethod: CalculationMethod
): Interpolation(pane, points) {
    init {
        refresh()
    }

    enum class CalculationMethod {
        deCasteljau,
        bernstein
    }

    override fun refresh() {
        var prev_eval = points[0]
        super.refresh()
        (0.0..1.0 step 0.01).forEach { t ->
            val eval = when(calculationMethod) {
                CalculationMethod.deCasteljau -> deCasteljau(t, points)
                CalculationMethod.bernstein -> bernstein(t, points[0], points[1], points[2], points[3])
            }
            pathGroup.children.add(Circle(eval.x, eval.y, 1.5).apply { fill = BetterColors.purple })
            arc_length += eval distance prev_eval
            prev_eval = eval
        }
        log()
    }

    companion object {
        /**
         * DeCasteljau Algorithm
         * Essentially Lerp until theres nothing left to lerp
         */
        fun deCasteljau(t: Double, dataPoints: ArrayList<Point>): Point {
            return when (dataPoints.size) {
                2 -> lerp(t, dataPoints[0], dataPoints[1])
                else -> deCasteljau(t, ArrayList((0 until dataPoints.size - 1).map { i ->
                    lerp(t, dataPoints[i], dataPoints[i+1])
                }))
            }
        }

        /**
         * Bernstein Algorithm
         * Expanded out form of DeCasteljau Algorithm, combined all the lerps into one big equation
         * Essentially a vector of each component scaled by the point
         */
        fun bernstein(t: Double, p0: Point, p1: Point, p2: Point, p3: Point): Point {
            return p0 * (-t.pow(3) + 3*t.pow(2) - 3*t + 1) +
                    p1 * (3*t.pow(3) - 6*t.pow(2) + 3*t) +
                    p2 * (-3*t.pow(3) + 3*t.pow(2)) +
                    p3 * (t.pow(3))
        }
    }

    override fun log() {
        telemetryManager
            .add("Method", "Bezier Calculation Method: $calculationMethod")
            .add("Arc Length", "Arc Length: ${arc_length round 3}")
            .update()
    }
}