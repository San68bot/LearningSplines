package com.san68bot.learningsplines.graphics.point

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.graphics.SplineGraphics.x_bounds
import com.san68bot.learningsplines.graphics.SplineGraphics.y_bounds
import com.san68bot.learningsplines.math.*
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class DynamicPoint(
    override var x: Double,
    override var y: Double,
    private val r: Double,
    private val id: String,
    private val c1: Color,
    private val c2: Color = c1,
    data: Boolean = false,
): Circle(x, y, r), Point by DataPoint(x, y) {
    init {
        if (!data) {
            fill = c1
            telemetryManager.add(id, "$id: ($x, $y)").update()
            update()
        }
    }

    constructor(x: Double, y: Double):
            this(x, y, Double.NaN, "", Color.BLACK, data = true)

    private fun update() {
        setOnMousePressed {
            fill = c2
            set(it)
        }
        setOnMouseDragged { set(it) }
        setOnMouseReleased { fill = c1 }
    }

    fun set(e: MouseEvent) = set(e.sceneX, e.sceneY)
    fun set(x_new: Double, y_new: Double) {
        centerX = x
        centerY = y

        translateX = clamp(x_new, x_bounds.first + r, x_bounds.second - r) - centerX
        translateY = clamp(y_new, y_bounds.first + r, y_bounds.second - r) - centerY

        x += translateX
        y += translateY

        Globals.update()
        telemetryManager.add(id, "$id: ($x, $y)").update()
    }
}