package com.san68bot.learningsplines.graphics

import com.san68bot.learningsplines.math.SplineMath.lerp
import javafx.scene.paint.Color

object BetterColors {
    val green: Color =
        Color.rgb(75, 228, 135)

    val orange: Color =
        Color.rgb(252, 188, 68)

    val yellow: Color =
        Color.rgb(249, 225, 102)

    val red: Color =
        Color.rgb(229, 15, 87)

    val blue: Color =
        Color.rgb(19, 122, 227)

    val light_blue: Color =
        Color.rgb(75, 196, 245)

    val purple: Color =
        Color.rgb(170, 39, 251)

    val white: Color =
        Color.rgb(250, 250, 250)

    fun gradient(a: Color, b: Color, t: Double): Color? {
        return Color.color(
            lerp(t, a.red, b.red),
            lerp(t, a.green, b.green),
            lerp(t, a.blue, b.blue),
        ).saturate()
    }
}