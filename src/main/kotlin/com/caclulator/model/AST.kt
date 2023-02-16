package com.caclulator.model

data class AST(
    val token: Token,
    val left: AST? = null,
    val right: AST? = null
)
