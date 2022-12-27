package com.san68bot.learningsplines.app

import com.san68bot.learningsplines.graphics.*
import com.san68bot.learningsplines.graphics.points.ControlPoint
import com.san68bot.learningsplines.graphics.points.DynamicPoint
import com.san68bot.learningsplines.graphics.points.Point
import com.san68bot.learningsplines.splines.Bezier
import com.san68bot.learningsplines.splines.BezierSpline
import com.san68bot.learningsplines.splines.Linear
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
        val c0 = ControlPoint(
            Point(150.0, 400.0),
            ControlPoint.ControlData(50.0, 30.0),
            "c0", BetterColors.red
        )

        val c1 = ControlPoint(
            Point(350.0, 400.0),
            ControlPoint.ControlData(50.0, 150.0),
            ControlPoint.ControlData(50.0, 330.0),
            "c1", BetterColors.light_blue
        )

        val c2 = ControlPoint(
            Point(550.0, 400.0),
            ControlPoint.ControlData(50.0, 210.0),
            ControlPoint.ControlData(50.0, 30.0),
            "c2", BetterColors.green
        )

        val c3 = ControlPoint(
            Point(750.0, 400.0),
            ControlPoint.ControlData(50.0, 150.0),
            "c3", BetterColors.yellow
        )

        BezierSpline(
            mainPane, arrayListOf(
                c0, c1, c2, c3
            )
        )
    }

    companion object {
        const val scene_width = 1440.0
        const val scene_height = 900.0
    }
}

fun main() {
    launch(Splines::class.java)
}