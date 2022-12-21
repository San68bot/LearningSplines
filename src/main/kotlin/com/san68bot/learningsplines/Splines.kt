package com.san68bot.learningsplines

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Splines : Application() {
    private val mainPane = Pane()

    override fun start(stage: Stage) {
        mainPane.children.addAll(SplineGraphics.graphics)
        stage.apply {
            scene = Scene(mainPane, scene_width, scene_height)
            title = "Splines"
            show()
        }
    }

    companion object {
        const val scene_width = 1440.0
        const val scene_height = 900.0
        @JvmStatic fun main(args: Array<String>) { launch(Splines::class.java) }
    }
}