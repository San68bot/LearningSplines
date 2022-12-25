package com.san68bot.learningsplines.app

import com.san68bot.learningsplines.graphics.*
import com.san68bot.learningsplines.splines.BezierInterpolation
import com.san68bot.learningsplines.splines.LinearInterpolation
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Splines : Application() {
    private val mainPane = Pane()
    override fun start(stage: Stage) {
        mainPane.children.addAll(SplineGraphics.graphics)
        splines()
        stage.apply {
            scene = Scene(mainPane, scene_width, scene_height)
            title = "Splines"
            show()
        }
    }

    private fun splines() {
        BezierInterpolation(mainPane, arrayOf(
            DynamicPoint(300.0, 500.0, 8.0, "Point 1", BetterColors.blue, BetterColors.orange),
            DynamicPoint(350.0, 300.0, 8.0, "Point 2", BetterColors.blue, BetterColors.orange),
            DynamicPoint(500.0, 300.0, 8.0, "Point 3", BetterColors.blue, BetterColors.orange),
            DynamicPoint(550.0, 500.0, 8.0, "Point 4", BetterColors.blue, BetterColors.orange)
        ))
    }

    companion object {
        const val scene_width = 1440.0
        const val scene_height = 900.0
    }
}

fun main() {
    launch(Splines::class.java)
}