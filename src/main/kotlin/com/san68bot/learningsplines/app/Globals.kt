package com.san68bot.learningsplines.app

import com.san68bot.learningsplines.graphics.SplineGraphics
import com.san68bot.learningsplines.graphics.TelemetryManager

object Globals {
    val telemetryManager =
        TelemetryManager(SplineGraphics.telemetry, ">>")

    var updateBlock: () -> Unit = {}
    private val updates = arrayListOf<() -> Unit>()

    fun addUpdate(block: () -> Unit) {
        updates.add(block)
    }

    fun update() {
        updates.forEach {
            it.invoke()
        }
    }
}