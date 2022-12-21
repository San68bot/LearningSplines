package com.san68bot.learningsplines.app

import com.san68bot.learningsplines.graphics.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Splines : Application() {
    private val mainPane = Pane()

    override fun start(stage: Stage) {
        val dp1 = DynamicPoint(300.0, 300.0, 8.0, "Point 1", BetterColors.green)
        val dp2 = DynamicPoint(500.0, 300.0, 8.0, "Point 2", BetterColors.blue)
        val dp3 = DynamicPoint(500.0, 500.0, 8.0, "Point 3", BetterColors.red)
        val dp4 = DynamicPoint(300.0, 500.0, 8.0, "Point 4", BetterColors.orange)

        mainPane.children.addAll(SplineGraphics.graphics)
        mainPane.children.addAll(dp1, dp2, dp3, dp4)

        stage.apply {
            scene = Scene(mainPane, scene_width, scene_height)
            title = "Splines"
            show()
        }
    }

    companion object {
        const val scene_width = 1440.0
        const val scene_height = 900.0
    }
}

fun main() {
    launch(Splines::class.java)
}