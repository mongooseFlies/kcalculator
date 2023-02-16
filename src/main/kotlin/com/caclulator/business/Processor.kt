package com.caclulator.business

import com.caclulator.model.*
import com.caclulator.util.divider
import java.lang.RuntimeException

class Processor(private val parser: Parser) {

    fun process(): Double {
        val astTree = parser.parse()
        return visit(astTree)
    }

    private fun visitNum(node: AST): Double = when (val token = node.token) {
        is Num -> token.value
        else -> throw RuntimeException()
    }

    private fun visitBinaryOp(node: AST): Double {
        val leftValue = visit(node.left!!)
        val rightValue = visit(node.right!!)

        return when (node.token) {
            Plus -> leftValue + rightValue
            Minus -> leftValue - rightValue
            Mul -> leftValue * rightValue
            Div -> divider(leftValue, rightValue)
            else -> throw RuntimeException()
        }
    }

    private fun visit(node: AST): Double = when (node.token) {
        is Num -> visitNum(node)
        Plus, Minus, Mul, Div -> visitBinaryOp(node)
        else -> throw RuntimeException()
    }
}