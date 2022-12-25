package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.app.Globals
import com.san68bot.learningsplines.graphics.DynamicPoint
import javafx.scene.Group
import javafx.scene.layout.Pane

abstract class Interpolation(
    pane: Pane,
    points: Array<DynamicPoint>,
    val pathGroup: Group = Group()
) {
    var arc_length: Double = 0.0

    init {
        Globals.updateBlock = { refresh() }
        pane.children.addAll(pathGroup)
        pane.children.addAll(points)
    }

    open fun refresh() {}
    open fun log() {}
}