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
        mainPane.children.addAll(SplineGraphics.graphics)
        val dp = DynamicPoint(300.0, 300.0, 8.0, "Point 1")
        val dp2 = DynamicPoint(500.0, 500.0, 8.0, "Point 2")
        mainPane.children.addAll(dp, dp2)
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