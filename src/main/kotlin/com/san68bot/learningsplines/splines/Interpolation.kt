package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.graphics.points.DynamicPoint
import com.san68bot.learningsplines.graphics.points.Point
import javafx.scene.Group
import javafx.scene.layout.Pane

abstract class Interpolation(
    pane: Pane,
    points: ArrayList<Point>,
    val pathGroup: Group = Group()
) {
    var arc_length: Double = 0.0

    init {
        Globals.addUpdate { refresh() }
        pane.children.addAll(pathGroup)
        points.forEach { point ->
            point.graphics.forEach { shape ->
                if (shape != null) pane.children.add(shape)
            }
        }
    }

    open fun refresh() {
        arc_length = 0.0
        pathGroup.children.clear()
    }
    open fun log() {}
}