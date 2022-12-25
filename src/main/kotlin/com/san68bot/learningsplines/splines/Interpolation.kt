package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.graphics.points.DynamicPoint
import javafx.scene.Group
import javafx.scene.layout.Pane

abstract class Interpolation(
    pane: Pane,
    points: ArrayList<DynamicPoint>,
    val pathGroup: Group = Group()
) {
    var arc_length: Double = 0.0

    init {
        Globals.addUpdate { refresh() }
        pane.children.addAll(pathGroup)
        pane.children.addAll(points)
    }

    open fun refresh() {
        arc_length = 0.0
        pathGroup.children.clear()
    }
    open fun log() {}
}