package com.caclulator

import org.amshove.kluent.`should be equal to`
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class KotlinCalculatorSpek : Spek({
    Feature("Calculator") {
        lateinit var calculator: KotlinCalculator

        Scenario("math expression with +- /* and nested ()") {
            mapOf(
                "(12 / (3 + 1) - 1) / (2 + 3) - 5 - 3 + 9" to 1.4,
                "88.8 * 12 / 123 + 1000 - 20 * (12 - (88 + 29.5/2))" to 2823.6634,
                "70 * 15.55555 / 10.999 + 14" to 112.9989,
                "(((12 + 99) * (25 - 100.5)) - 125)" to -8505.5
            ).forEach { (input, expected) ->
                When("complex math expression") {
                    calculator = KotlinCalculator()
                }

                Then("should return the correct result") {
                    calculator.calculate(input) `should be equal to` expected.toString()
                }
            }
        }

        Scenario("complex math expression of doubles") {
            mapOf(
                "(12.9 / (3.14 + 1) - 1) / (21.9 + 333)" to 0.006,
                "88.8 * 12.14 / 123 + 1000 - 20 * (12 - (88 + 29.5/2))" to 2823.7645,
                "70 * 15.55555 / 10.999 + 14" to 112.9989,
                "(((12/14 + 99/88) * (25 - 100.5)) - 125.15555555)" to -274.8073
            ).forEach { (input, expected) ->
                When("calculated operation") {
                    calculator = KotlinCalculator()
                }

                Then("should return the correct result rounded by 4 digits only") {
                    val result = calculator.calculate(input)
                    result `should be equal to` expected.toString()
                    result.isRoundedByMax(4) `should be equal to` true
                }
            }
        }
    }
})

fun String.isRoundedByMax(decimals: Int) =
    """(-?\d+)(\.(\d+))?""".toRegex()
        .matchEntire(this)
        ?.destructured
        ?.let { (_, _, decimalValue) ->
            decimalValue.length <= decimals
        } ?: false