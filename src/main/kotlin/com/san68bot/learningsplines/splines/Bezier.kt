package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.points.DynamicPoint
import com.san68bot.learningsplines.math.SplineMath.lerp
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
class Bezier(
    pane: Pane,
    private val points: ArrayList<DynamicPoint>,
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

    /**
     * DeCasteljau Algorithm
     * Essentially Lerp until theres nothing left to lerp
     */
    private fun deCasteljau(t: Double, dataPoints: ArrayList<DynamicPoint>): DynamicPoint {
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
    private fun bernstein(t: Double, p0: Double, p1: Double, p2: Double, p3: Double): Double {
        return p0 * (-t.pow(3) + 3*t.pow(2) - 3*t + 1) +
               p1 * (3*t.pow(3) - 6*t.pow(2) + 3*t) +
               p2 * (-3*t.pow(3) + 3*t.pow(2)) +
               p3 * (t.pow(3))
    }
    private fun bernstein(t: Double, p0: DynamicPoint, p1: DynamicPoint, p2: DynamicPoint, p3: DynamicPoint): DynamicPoint {
        return DynamicPoint(
            bernstein(t, p0.x, p1.x, p2.x, p3.x),
            bernstein(t, p0.y, p1.y, p2.y, p3.y)
        )
    }

    override fun log() {
        telemetryManager
            .add("Method", "Bezier Calculation Method: $calculationMethod")
            .add("Arc Length", "Arc Length: ${arc_length round 3}")
            .update()
    }
}