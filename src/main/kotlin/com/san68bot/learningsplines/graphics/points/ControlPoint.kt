package com.san68bot.learningsplines.graphics.points

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.SplineGraphics.x_bounds
import com.san68bot.learningsplines.graphics.SplineGraphics.y_bounds
import com.san68bot.learningsplines.math.angleWrap
import com.san68bot.learningsplines.math.clamp
import com.san68bot.learningsplines.math.round
import com.san68bot.learningsplines.math.toRadians
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Shape
import kotlin.math.*

class ControlPoint(
    origin: Point,
    tangent1: ControlData,
    tangent2: ControlData,
    private val id: String,
    private val color: Color
): Point(origin.x, origin.y) {

    constructor(origin: Point, tangent1: ControlData, id: String, color: Color):
            this(origin, tangent1, ControlData(Double.NaN, Double.NaN), id, color)

    val t0 = ControlVector(tangent1)
    val t1 = ControlVector(tangent2)

    data class ControlData(var magnitude: Double, var angle: Double)
    inner class ControlVector(val cd: ControlData): Point(
        this@ControlPoint.x + cd.magnitude * cos(cd.angle.toRadians()),
        this@ControlPoint.y + cd.magnitude * sin(cd.angle.toRadians())
    ) {
        private val cx get() = this@ControlPoint.x
        private val cy get() = this@ControlPoint.y
        val line = Line(cx, cy, x, y)

        init {
            radius = 5.0
            fill = Color.rgb(30, 32, 41)
            stroke = color
            strokeWidth = 2.0
            centerX = x
            centerY = y
            line.apply {
                stroke = color
                strokeWidth = 5.0
            }
            construct()
        }

        private fun construct() {
            setOnMousePressed {
                stroke = BetterColors.white
                set(it)
                Globals.update()
            }
            setOnMouseDragged {
                set(it)
                Globals.update()
            }
            setOnMouseReleased {
                stroke = color
                set(it)
                Globals.update()
            }
        }

        fun linkedMove() {
            x = cx + cd.magnitude * cos(cd.angle.toRadians())
            y = cy + cd.magnitude * sin(cd.angle.toRadians())

            centerX = x
            centerY = y

            line.apply {
                startX = cx
                startY = cy
                endX = x
                endY = y
            }
        }

        private fun set(e: MouseEvent) = set(e.sceneX, e.sceneY)
        fun set(x_new: Double, y_new: Double) {
            centerX = x
            centerY = y

            translateX = (clamp(x_new, x_bounds.first + radius, x_bounds.second - radius) - centerX)
            translateY = (clamp(y_new, y_bounds.first + radius, y_bounds.second - radius) - centerY)

            line.apply {
                startX = cx
                startY = cy
                endX = x
                endY = y
            }

            x += translateX
            y += translateY

            cd.magnitude = this@ControlPoint distance this
            cd.angle = this@ControlPoint angle this
            telemetry()
        }
    }

    init {
        fill = color
        radius = 8.0
        telemetry()
        update()
    }

    private fun update() {
        setOnMousePressed {
            fill = BetterColors.white
            set(it)
            Globals.update()
        }
        setOnMouseDragged {
            set(it)
            Globals.update()
        }
        setOnMouseReleased {
            fill = color
            set(it)
            Globals.update()
        }
    }

    private fun set(e: MouseEvent) = set(e.sceneX, e.sceneY)
    fun set(x_new: Double, y_new: Double) {
        centerX = x
        centerY = y

        translateX = clamp(x_new, x_bounds.first + radius, x_bounds.second - radius) - centerX
        translateY = clamp(y_new, y_bounds.first + radius, y_bounds.second - radius) - centerY

        x += translateX
        y += translateY

        t0.linkedMove()
        t1.linkedMove()

        telemetry()
    }

    fun telemetry() {
        telemetryManager
            .add(id, "$id: (${x round 3}, ${y round 3})")
            .add("$id t1", "$id t1: ${t0.cd.magnitude round 3}, ${t0.cd.angle round 3}°")
            .add("$id t2", "$id t2: ${t1.cd.magnitude round 3}, ${t1.cd.angle round 3}°\n")
            .update()
    }

    override var graphics: List<Shape?> = listOf(t0.line, t1.line, this, t0, t1)
}