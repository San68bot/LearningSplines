package com.san68bot.learningsplines.graphics.points

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.BetterColors
import com.san68bot.learningsplines.graphics.SplineGraphics.x_bounds
import com.san68bot.learningsplines.graphics.SplineGraphics.y_bounds
import com.san68bot.learningsplines.math.*
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Shape

class DynamicPoint(
    override var x: Double,
    override var y: Double,
    r: Double,
    private val id: String,
    private val color: Color,
    data: Boolean = false
): Point(x, y, r) {
    init {
        if (!data) {
            fill = color
            telemetryManager.add(id, "$id: ($x, $y)").update()
            update()
        }
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

        telemetryManager.add(id, "$id: (${x round 3}, ${y round 3})").update()
    }

    override var graphics: List<Shape?> = listOf(this)
}