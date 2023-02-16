package com.caclulator.util

import kotlin.math.pow
import kotlin.math.roundToInt

val divider = { left: Double, right: Double ->
    if (right == 0.0) throw IllegalArgumentException()
    else
        left / right
}

fun String.isValidDouble() = let {
    val doubleReg = """\d+(\.\d+)?""".toRegex()
    doubleReg.matches(it)
}

fun Double.round(decimals: Int = 4): Double {
    val factor = 10.0.pow(decimals)
    return (this * factor).roundToInt() / factor
}

inline fun <T> T.throwIf(predicate: (T) -> Boolean, ex: () -> Throwable) = if (predicate(this)) throw ex() else this

inline fun throwIf(predicate: Boolean, ex: () -> Throwable) = when {
    predicate -> throw ex()
    else -> {}
}