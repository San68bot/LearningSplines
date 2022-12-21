package com.san68bot.learningsplines.splines

import com.san68bot.learningsplines.graphics.DynamicPoint
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.Line

class LinearInterpolation(
    private val pane: Pane,
    private val points: Array<DynamicPoint>
) {
    private val lineList = ArrayList<Line>(points.size - 1)

    init {
        setup()
        update()
    }

    private fun setup() {
        (0 until points.size - 1).forEach { i ->
            lineList.add(
                Line(points[i].x, points[i].y, points[i+1].x, points[i+1].y).apply {
                    fill = Color.WHITE
                    stroke = Color.WHITE
                    strokeWidth = 5.0
                }
            )
        }
        pane.children.addAll(lineList)
        pane.children.addAll(points)
    }

    fun update() {
        lineList.forEachIndexed { i, line ->
            line.apply {
                startX = points[i].x
                startY = points[i].y
                endX = points[i+1].x
                endY = points[i+1].y
            }
        }
    }
}