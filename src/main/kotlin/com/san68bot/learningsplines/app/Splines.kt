package com.san68bot.learningsplines.app

import com.san68bot.learningsplines.graphics.*
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
        val li = LinearInterpolation(mainPane, arrayOf(
            DynamicPoint(300.0, 300.0, 8.0, "Point 1", BetterColors.green),
            DynamicPoint(500.0, 300.0, 8.0, "Point 2", BetterColors.blue),
            DynamicPoint(500.0, 500.0, 8.0, "Point 3", BetterColors.red),
            DynamicPoint(300.0, 500.0, 8.0, "Point 4", BetterColors.orange)
        ))

        Globals.toUpdate {
            li.refresh()
        }

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