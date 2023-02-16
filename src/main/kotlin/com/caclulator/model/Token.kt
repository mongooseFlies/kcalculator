package com.caclulator.model

sealed class Token

data class Num(val value: Double) : Token() {
    override fun toString() = value.toString()
}

object Plus : Token() {
    override fun toString() = "+"
}

object Minus : Token() {
    override fun toString() = "-"
}

object Mul : Token() {
    override fun toString() = "*"
}

object Div : Token() {
    override fun toString() = "/"
}

object LP : Token() {
    override fun toString() = "("
}

object RP : Token() {
    override fun toString() = ")"
}

object EOF : Token() {
    override fun toString() = ";"
}