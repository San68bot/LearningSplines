package com.san68bot.learningsplines

import javafx.scene.control.Label
import javafx.scene.effect.InnerShadow
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment

object SplineGraphics {
    val mapRect = Rectangle(0.0, 0.0, Splines.scene_width, Splines.scene_height).apply {
        fill = Color.rgb(255, 255, 255)
    }

    val backgroundRect = Rectangle(940.0, 0.0, 500.0, 900.0).apply {
        fill = Color.rgb(31, 38, 46)
        effect = InnerShadow(35.0, Color.rgb(0, 0, 0)).apply {
            width = 50.0
            height = 50.0
        }
    }

    val telemetryText = Text(1082.0, 50.0, "Telemetry").apply {
        font = Font("Avenir Book", 48.0)
        fill = Color.rgb(255, 255, 255)
    }

    val telemetryRect = Rectangle(965.0, 75.0, 450.0, 700.0).apply {
        fill = Color.rgb(21, 26, 41)
        arcWidth = 50.0
        arcHeight = 50.0
        stroke = Color.rgb(0, 0, 0)
        strokeWidth = 1.0
    }

    val telemetry = Label(">> Hello World!").apply {
        font = Font("Andale Mono", 15.0)
        textFill = Color.rgb(255, 255, 255)
        textAlignment = TextAlignment.LEFT
        prefWidth = 390.0
        layoutX = 1000.0
        layoutY = 100.0
    }

    val graphics = listOf(mapRect, backgroundRect, telemetryText, telemetryRect, telemetry)
}