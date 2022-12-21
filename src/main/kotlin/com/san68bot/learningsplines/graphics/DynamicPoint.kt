package com.san68bot.learningsplines.graphics

import com.san68bot.learningsplines.app.Globals.telemetryManager
import com.san68bot.learningsplines.math.*
import javafx.scene.input.MouseEvent
import javafx.scene.paint.Color
import javafx.scene.shape.Circle

class DynamicPoint(
    var x: Double,
    var y: Double,
    val r: Double,
    private val id: String
): Circle(x, y, r) {
    init {
        fill = Color.rgb(19, 122, 227)
        telemetryManager.add(id, "$id: ($x, $y)").update()
        drag()
    }

    private fun drag() {
        setOnMousePressed { e ->
            x = centerX
            y = centerY
            transposePosition(e)
        }
        setOnMouseDragged { e -> transposePosition(e) }
    }

    private fun transposePosition(e: MouseEvent) {
        val clamped_x = clamp(e.sceneX, 1.0 + r, 939.5 - r)
        val clamped_y = clamp(e.sceneY, 2.0 + r, 792.0 - r)
        translateX = clamped_x - x
        translateY = clamped_y - y
        telemetryManager.add(id, "$id: ($clamped_x, $clamped_y)").update()
    }
}