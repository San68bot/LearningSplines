package com.san68bot.learningsplines.app

import com.san68bot.learningsplines.graphics.SplineGraphics
import com.san68bot.learningsplines.graphics.TelemetryManager

object Globals {
    val telemetryManager =
        TelemetryManager(SplineGraphics.telemetry, ">>")

    var updateBlock: () -> Unit = {}

    fun toUpdate(block: () -> Unit) {
        updateBlock = block
    }

    fun update() {
        updateBlock.invoke()
    }
}