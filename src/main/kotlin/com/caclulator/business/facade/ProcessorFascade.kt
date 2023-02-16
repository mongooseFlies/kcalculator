package com.caclulator.business.facade

import com.caclulator.business.Lexer
import com.caclulator.business.Parser
import com.caclulator.business.Processor

class ProcessorFacade {
    fun processInput(text: String): Double {
        val lexer = Lexer(text)
        val parser = Parser(lexer)
        return Processor(parser).process()
    }
}