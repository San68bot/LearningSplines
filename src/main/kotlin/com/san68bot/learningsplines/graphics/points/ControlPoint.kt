package com.san68bot.learningsplines.graphics.points

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.SplineGraphics.x_bounds
import com.san68bot.learningsplines.graphics.SplineGraphics.y_bounds
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
    tangent2: ControlData?,
    private val id: String,
    private val color: Color
): Point(origin.x, origin.y) {
    val t0 = ControlVector(tangent1)
    val t1 = if (tangent2 == null) null else ControlVector(tangent2)

    data class ControlData(var magnitude: Double, var angle: Double)
    inner class ControlVector(val cd: ControlData): Circle() {
        var vx = x + cd.magnitude * cos(cd.angle.toRadians())
        var vy = y + cd.magnitude * sin(cd.angle.toRadians())
        val line = Line(x, y, vx, vy)

        init {
            radius = 5.0
            fill = Color.rgb(30, 32, 41)
            stroke = color
            strokeWidth = 2.0
            centerX = vx
            centerY = vy
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
            vx = x + cd.magnitude * cos(cd.angle.toRadians())
            vy = y + cd.magnitude * sin(cd.angle.toRadians())

            centerX = vx
            centerY = vy

            line.apply {
                startX = x
                startY = y
                endX = vx
                endY = vy
            }
        }

        private fun set(e: MouseEvent) = set(e.sceneX, e.sceneY)
        fun set(x_new: Double, y_new: Double) {
            centerX = vx
            centerY = vy

            translateX = (clamp(x_new, x_bounds.first + radius, x_bounds.second - radius) - centerX)
            translateY = (clamp(y_new, y_bounds.first + radius, y_bounds.second - radius) - centerY)

            line.apply {
                startX = x
                startY = y
                endX = vx
                endY = vy
            }

            vx += translateX
            vy += translateY

            cd.magnitude = this@ControlPoint distance Point(vx, vy)
            cd.angle = this@ControlPoint angle Point(vx, vy)
        }
    }

    init {
        fill = color
        radius = 8.0
        telemetryManager.add(id, "$id: ($x, $y)").update()
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
        t1?.linkedMove()

        telemetryManager.add(id, "$id: (${x round 3}, ${y round 3})").update()
    }

    override var graphics: List<Shape?> = listOf(t0.line, t1?.line, this, t0, t1)
}