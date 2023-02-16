package com.caclulator

import com.caclulator.business.Lexer
import com.caclulator.business.Parser
import com.caclulator.business.exception.InvalidSyntaxException
import com.caclulator.model.*
import org.amshove.kluent.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

object ParserSpec : Spek({
    Feature("Parser") {
        lateinit var parser: Parser

        Scenario("one digit number") {
            When("parsing 1 digit number") {
                val input = "1"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should return 1 AST number node") {
                val astNode = parser.parse()
                astNode.token `should be equal to` Num(1.0)
                astNode.left `should be equal to` null
                astNode.right `should be equal to` null
            }
        }

        Scenario("two digit number") {
            When("parsing 2 digits number") {
                val input = "10"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should return AST node with 2 digit number") {
                val astNode = parser.parse()
                astNode.token `should be equal to` Num(10.0)
                astNode.left `should be equal to` null
                astNode.right `should be equal to` null
            }
        }

        Scenario("addition of 2 numbers") {
            When("parsing addition of 2 numbers") {
                val input = "100 + 29"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should return the correct AST tree") {
                val astTree = parser.parse()
                astTree.token `should be equal to` Plus
                astTree.left?.token `should be equal to` Num(100.0)
                astTree.right?.token `should be equal to` Num(29.0)
            }
        }

        Scenario("subtraction of 2 numbers") {
            When("parsing subtraction of 2 numbers") {
                val input = "40 - 30"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should return the correct AST tree") {
                val astTree = parser.parse()
                astTree.token `should be equal to` Minus
                astTree.left?.token `should be equal to` Num(40.0)
                astTree.right?.token `should be equal to` Num(30.0)
            }
        }

        Scenario("Multiplication of 2 numbers") {
            When("parsing multiplication of 2 numbers") {
                val input = "4 * 12"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should return tokens correctly") {
                val astTree = parser.parse()
                astTree.token `should be equal to` Mul
                astTree.left?.token `should be equal to` Num(4.0)
                astTree.right?.token `should be equal to` Num(12.0)
            }
        }

        Scenario("Division of 2 numbers") {
            When("parsing division of 2 numbers") {
                val input = "120 / 20"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should return the correct AST tree") {
                val astTree = parser.parse()
                astTree.token `should be equal to` Div
                astTree.left?.token `should be equal to` Num(120.0)
                astTree.right?.token `should be equal to` Num(20.0)
            }
        }

        Scenario("math operation with parenthesis") {
            When("parsing any math operation with ()") {
                val input = "7 * (3 + 4)"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }

            Then("should respect the precedence of math operation and parenthesis") {
                val astTree = parser.parse()
                astTree.token `should be equal to` Mul
                astTree.left?.token `should be equal to` Num(7.0)
                astTree.right?.token `should be equal to` Plus
                astTree.right?.left?.token `should be equal to` Num(3.0)
                astTree.right?.right?.token `should be equal to` Num(4.0)
            }
        }

        Scenario("invalid mathematical expression") {
            When("parsing invalid mathematical expression") {
                val input = "10 + 5 + 15 - )"
                val lexer = Lexer(input)
                parser = Parser(lexer)
            }
            Then("should throw InvalidSyntaxException") {
                invoking { parser.parse() } shouldThrow InvalidSyntaxException::class
            }
        }
    }
})