package com.caclulator

import com.caclulator.business.facade.ProcessorFacade
import com.caclulator.util.round

class KotlinCalculator {
    private var processor: ProcessorFacade = ProcessorFacade()

    fun calculate(input: String): String =
        processor.processInput(input).round().toString()
}