package com.caclulator.business

import com.caclulator.business.exception.InvalidSyntaxException
import com.caclulator.model.*
import com.caclulator.util.isValidDouble
import com.caclulator.util.throwIf

class Lexer(
    private var text: String,
    private var pos: Int = 0,
    private var currentChar: Char? = null
) {

    init {
        currentChar = when {
            text.isNotEmpty() -> text[0]
            else -> null
        }
    }

    fun getNextToken(): Token = currentChar?.let {
        skipWhiteSpace()
        if (currentChar in ('0'..'9'))
            return Num(number())
        val token = when (currentChar) {
            '+' -> Plus
            '-' -> Minus
            '*' -> Mul
            '/' -> Div
            '(' -> LP
            ')' -> RP
            else -> throw InvalidSyntaxException()
        }
        next()
        return token
    } ?: EOF

    private fun next() {
        ++pos
        currentChar = when (pos) {
            in (text.indices) -> text[pos]
            else -> null
        }
    }

    private fun skipWhiteSpace() {
        while (currentChar == ' ')
            next()
    }

    private fun number(): Double {
        var result = ""
        while (currentChar?.isDigit() == true || currentChar == '.') {
            result += currentChar
            next()
        }
        return result.throwIf({ !it.isValidDouble() }) {
            InvalidSyntaxException()
        }.toDouble()
    }

    fun clear(){
        text = ""
        pos = 0
        currentChar = null
    }
}