package com.san68bot.learningsplines.graphics

import javafx.scene.control.Label

data class Data(val id: String, var text: String)

class TelemetryManager(
    private val telemetry: Label,
    private val prefix: String
) {
    private val telemetryStrings = mutableListOf<Data>()

    fun add(id: String, text: String): TelemetryManager {
        telemetryStrings.forEach {
            if (it.id == id) {
                it.text = text
                return this
            }
        }
        telemetryStrings.add(Data(id, text))
        return this
    }

    fun update(): TelemetryManager {
        var output = ""
        telemetryStrings.forEachIndexed { i: Int, d: Data ->
            output += "$prefix " + (if (i < telemetryStrings.size - 1) "${d.text} \n" else d.text)
        }
        telemetry.text = output
        return this
    }
}