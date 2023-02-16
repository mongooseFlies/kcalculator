package com.caclulator.business

import com.caclulator.business.exception.InvalidSyntaxException
import com.caclulator.model.*
import com.caclulator.util.throwIf

class Parser(
    private val lexer: Lexer,
    private var currentToken: Token? = null
) {
    init {
        currentToken = lexer.getNextToken()
    }

    fun parse(): AST = expr()

    private fun expr(): AST {
        var node = term()
        while (currentToken in listOf(Plus, Minus)) {
            when (currentToken) {
                Plus -> {
                    expect(Plus)
                    val left = node
                    val right = term()
                    node = AST(Plus, left, right)
                }

                Minus -> {
                    expect(Minus)
                    val left = node
                    val right = term()
                    node = AST(Minus, left, right)
                }

                else -> throw InvalidSyntaxException()
            }
        }
        return node
    }

    private fun term(): AST {
        var node = factor()
        while (currentToken in listOf(Mul, Div)) {
            when (currentToken) {
                Mul -> {
                    expect(Mul)
                    val left = node
                    val right = factor()
                    node = AST(Mul, left, right)
                }

                Div -> {
                    expect(Div)
                    val left = node
                    val right = factor()
                    node = AST(Div, left, right)
                }

                else -> throw InvalidSyntaxException()
            }
        }
        return node
    }

    private fun factor(): AST = when (val token = currentToken) {
        is Num -> {
            expect(Num(token.value))
            AST(token)
        }

        is LP -> {
            expect(LP)
            val node = expr()
            expect(RP)
            node
        }

        else -> throw InvalidSyntaxException()
    }

    private fun expect(token: Token) {
        throwIf(token != currentToken) { InvalidSyntaxException() }
        currentToken = lexer.getNextToken()
    }
}