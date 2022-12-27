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
    val color: Color
): Point(origin.x, origin.y) {
    constructor(origin: Point, tangent1: ControlData, id: String, color: Color):
            this(origin, tangent1, ControlData(Double.NaN, Double.NaN, Movement.Free), id, color)

    val t1 = ControlVector(tangent1)
    val t2 = ControlVector(tangent2)
    val t2Active get() = (!t2.magnitude.isNaN() && !t2.magnitude.isNaN())

    private fun otherTangent(tangent: ControlVector): ControlVector {
        return if (tangent == t1) t2 else t1
    }

    enum class Movement {
        Free, Aligned, Mirrored;
        fun isFree() = (this == Free)
        fun isAligned() = (this == Aligned)
        fun isMirrored() = (this == Mirrored)
    }
    data class ControlData(val magnitude: Double, val angle: Double, val movement: Movement = Movement.Free)
    inner class ControlVector(cd: ControlData): Point(
        this@ControlPoint.x + cd.magnitude * cos(cd.angle.toRadians()),
        this@ControlPoint.y - cd.magnitude * sin(cd.angle.toRadians())
    ) {
        private val cx get() = this@ControlPoint.x
        private val cy get() = this@ControlPoint.y
        val line = Line(cx, cy, x, y)

        var magnitude = cd.magnitude
        var angle = cd.angle
        private val movement = cd.movement

        init {
            radius = 5.0
            fill = Color.rgb(30, 32, 41)
            stroke = color
            strokeWidth = 2.5
            centerX = x
            centerY = y
            line.apply {
                stroke = color
                strokeWidth = 4.0
            }
            construct()
        }

        private fun construct() {
            setOnMousePressed {
                stroke = BetterColors.white
                onMouseEvent(it)
            }
            setOnMouseDragged {
                onMouseEvent(it)
            }
            setOnMouseReleased {
                stroke = color
                onMouseEvent(it)
            }
        }

        private fun onMouseEvent(me: MouseEvent) {
            set(me)
            conditionalMovement()
        }

        private fun set(e: MouseEvent) = set(e.sceneX, e.sceneY)
        fun set(x_new: Double, y_new: Double) {
            centerX = x
            centerY = y

            translateX = (clamp(x_new, x_bounds.first + radius, x_bounds.second - radius) - centerX)
            translateY = (clamp(y_new, y_bounds.first + radius, y_bounds.second - radius) - centerY)

            updateLine()

            x += translateX
            y += translateY

            magnitude = this@ControlPoint distance this
            angle = this@ControlPoint angle this

            telemetry()
            Globals.update()
        }

        private fun conditionalMovement() {
            val otherTangent = otherTangent(this)
            if (movement.isMirrored() || otherTangent.movement.isMirrored()) {
                otherTangent.angle(angleWrap(angle + 180.0))
                otherTangent.magnitude(magnitude)
            }
            if (movement.isAligned() || otherTangent.movement.isAligned()) {
                otherTangent.angle(angleWrap(angle + 180.0))
            }

            updateLine()
            otherTangent.updateLine()
        }
        
        private fun updateLine() {
            line.apply {
                startX = cx
                startY = cy
                endX = x
                endY = y
            }
        }

        fun angle(a0: Double) {
            set(
                cx + magnitude * cos(a0.toRadians()),
                cy - magnitude * sin(a0.toRadians())
            )
        }

        fun magnitude(m0: Double) {
            set(
                cx + m0 * cos(angle.toRadians()),
                cy - m0 * sin(angle.toRadians())
            )
        }

        fun linkedMove() {
            set(
                cx + magnitude * cos(angle.toRadians()),
                cy - magnitude * sin(angle.toRadians())
            )
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
        }
        setOnMouseDragged {
            set(it)
        }
        setOnMouseReleased {
            fill = color
            set(it)
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

        t1.linkedMove()
        t2.linkedMove()

        telemetry()
        Globals.update()
    }

    fun telemetry() {
        telemetryManager
            .add(id, "$id: (${x round 3}, ${y round 3})")
            .add("$id t1", "$id t1: ${t1.magnitude round 3}, ${t1.angle round 3}°")
            .add("$id t2", "$id t2: ${t2.magnitude round 3}, ${t2.angle round 3}°\n")
            .update()
    }

    override var graphics: List<Shape?> = listOf(t1.line, t2.line, this, t1, t2)
}