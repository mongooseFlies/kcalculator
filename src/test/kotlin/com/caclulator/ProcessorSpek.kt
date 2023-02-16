package com.caclulator

import com.caclulator.business.Lexer
import com.caclulator.business.Parser
import com.caclulator.business.Processor
import com.caclulator.model.AST
import com.caclulator.model.Num
import com.caclulator.model.Plus
import io.mockk.every
import io.mockk.mockk
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldThrow
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class ProcessorSpek : Spek({
    Feature("Processor") {
        lateinit var processor: Processor
        lateinit var parser: Parser
        lateinit var lexer: Lexer

        Scenario("simple math operation") {
            val input = "10 + 30"
            parser = mockk()
            processor = Processor(parser)
            When("given an AST tree of $input") {
                val leftNode = AST(Num(10.0))
                val rightNode = AST(Num(30.0))
                val rootNode = AST(Plus, leftNode, rightNode)
                every { parser.parse() } returns rootNode
            }

            Then("should return the sum of both numbers") {
                processor.process() `should be equal to` 40.0
            }
        }

        Scenario("complex math operations") {
            mapOf(
                "(10 - 150) / 44 * (99 - (4/9 + 30))" to -218.13131313131314,
                "10/13 + 3.14 * (12 - 99.99) / 14 * 99" to -1952.9858692307691
            ).forEach { (input, expected) ->
                When("complex math operations including nested ()"){
                    lexer = Lexer(input)
                    parser = Parser(lexer)
                    processor = Processor(parser)
                }
                Then("should return the result of the entire expression respecting (), precedence") {
                    processor.process() `should be equal to` expected
                }
            }
        }

        Scenario("expression with zero division") {
                When("complex math operations including nested ()"){
                    val input = "21 + 4/0"
                    lexer = Lexer(input)
                    parser = Parser(lexer)
                    processor = Processor(parser)
                }
                Then("should return the result of the entire expression respecting (), precedence") {
                    invoking {processor.process()} shouldThrow IllegalArgumentException::class
                }
        }

    }
})