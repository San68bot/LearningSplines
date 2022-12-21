package com.san68bot.learningsplines.graphics

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.SplineGraphics.x_bounds
import com.san68bot.learningsplines.graphics.SplineGraphics.y_bounds
import com.san68bot.learningsplines.math.*
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class DynamicPoint(
    var x: Double,
    var y: Double,
    private val r: Double,
    private val id: String,
    color: Color
): Circle(x, y, r) {
    init {
        fill = color
        telemetryManager.add(id, "$id: ($x, $y)").update()
        update()
    }

    private fun update() {
        setOnMousePressed { set(it) }
        setOnMouseDragged { set(it) }
    }

    fun set(e: MouseEvent) = set(e.sceneX, e.sceneY)
    fun set(x_new: Double, y_new: Double) {
        centerX = x
        centerY = y

        translateX = clamp(x_new, x_bounds.first + r, x_bounds.second - r) - centerX
        translateY = clamp(y_new, y_bounds.first + r, y_bounds.second - r) - centerY

        x += translateX
        y += translateY

        telemetryManager.add(id, "$id: ($x, $y)").update()
    }
}