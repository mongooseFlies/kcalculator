package com.caclulator

import com.caclulator.business.Lexer
import com.caclulator.model.*
import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class LexerSpec: Spek( {
    Feature("Lexer") {
        lateinit var lexer: Lexer

        Scenario("one digit number") {
            When("parsing 1 digit number") {
                val input = "1"
                lexer = Lexer(input)
            }

            Then("should return one token with the given number") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(1.0)
                lexer.getNextToken() `should be equal to` EOF
            }
        }

        Scenario("two digit number") {
            When("parsing 2 digit number"){
                    val input = "10"
                lexer = Lexer(input)
            }

            Then("should return token with 2 digit number") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(10.0)
                nextToken(lexer) `should be equal to` EOF
            }

        }

        Scenario("addition of 2 numbers") {
            When("parsing addition") {
                val input = "100+29"
                lexer = Lexer(input)
            }

            Then("should return tokens correctly") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(100.0)
                nextToken(lexer) `should be equal to` Plus
                nextToken(lexer) `should be equal to` Num(29.0)
                nextToken(lexer) `should be equal to` EOF
            }
        }

        Scenario("subtraction of 2 numbers") {
            When("parsing addition") {
                val input = "40 - 30"
                lexer = Lexer(input)
            }

            Then("should return tokens correctly") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(40.0)
                nextToken(lexer) `should be equal to` Minus
                nextToken(lexer) `should be equal to` Num(30.0)
                nextToken(lexer) `should be equal to` EOF
            }
        }

        Scenario("Multiplication of 2 numbers") {
            When("parsing multiplication") {
                val input = "4 * 12"
                lexer = Lexer(input)
            }

            Then("should return tokens correctly") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(4.0)
                nextToken(lexer) `should be equal to` Mul
                nextToken(lexer) `should be equal to` Num(12.0)
                nextToken(lexer) `should be equal to` EOF
            }
        }

        Scenario("Division of 2 numbers") {
            When("parsing division") {
                val input = "120/20"
                lexer = Lexer(input)
            }

            Then("should return tokens correctly") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(120.0)
                nextToken(lexer) `should be equal to` Div
                nextToken(lexer) `should be equal to` Num(20.0)
                nextToken(lexer) `should be equal to` EOF
            }
        }

        Scenario("any mathematical operation that has whitespaces") {
            When("parsing addition") {
                val input = "120 / 20 +10- 4"
                lexer = Lexer(input)
            }

            Then("should return tokens correctly") {
                val currentToken = lexer.getNextToken()
                currentToken `should be equal to` Num(120.0)
                nextToken(lexer) `should be equal to` Div
                nextToken(lexer) `should be equal to` Num(20.0)
                nextToken(lexer) `should be equal to` Plus
                nextToken(lexer) `should be equal to` Num(10.0)
                nextToken(lexer) `should be equal to` Minus
                nextToken(lexer) `should be equal to` Num(4.0)
                nextToken(lexer) `should be equal to` EOF
            }
        }

        Scenario("any math operation with parenthesis") {
            When("parsing expression with parenthesis") {
                val input = "7 * (3 + 4)"
                lexer = Lexer(input)
            }

            Then("should return tokens correctly including parenthesis") {
                val currentToken = lexer.getNextToken()

                currentToken `should be equal to` Num(7.0)
                nextToken(lexer) `should be equal to` Mul
                nextToken(lexer) `should be equal to` LP
                nextToken(lexer) `should be equal to` Num(3.0)
                nextToken(lexer) `should be equal to` Plus
                nextToken(lexer) `should be equal to` Num(4.0)
                nextToken(lexer) `should be equal to` RP
                nextToken(lexer) `should be equal to` EOF
            }
        }
    }
})

private fun nextToken(lexer: Lexer) = lexer.getNextToken()