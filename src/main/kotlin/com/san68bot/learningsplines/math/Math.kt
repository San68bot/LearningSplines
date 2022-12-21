package com.san68bot.learningsplines.math

import kotlin.math.*

infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
    val sequence = generateSequence(start) { previous ->
        if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}

infix fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}

fun Double.toRadians(): Double = this * PI / 180.0

fun angleWrap(angle: Double): Double {
    var angle0 = angle
    while (angle < 0) angle0 += 360.0
    while (angle > 360) angle0 -= 360.0
    return angle0
}


fun clamp(value: Double, min: Double, max: Double): Double = min(max(value, min), max)
fun clamp(value: Double, range: ClosedFloatingPointRange<Double>): Double = clamp(value, range.start, range.endInclusive)