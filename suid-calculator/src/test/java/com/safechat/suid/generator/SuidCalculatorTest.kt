package com.safechat.suid.generator

import org.junit.Assert.assertEquals
import org.junit.Test

class SuidCalculatorTest {

    val suidCalculator = SuidCalculatorImpl()

    @Test
    fun shouldFindShortestUniqueSubstringSimple() {
        val input = listOf("kotlin")

        assertEquals("la", suidCalculator.findShortestUniqueSubstring("kotlan", input))
    }

    @Test
    fun shouldFindShortestUniqueSubstringComplex() {
        val input = listOf("k2akper", "kak2per")

        assertEquals("kakp", suidCalculator.findShortestUniqueSubstring("kakper", input))
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowIllegalArgumentExceptionIfOriginalStringIsOnTheList() {
        val original = "kotlin"
        val input = listOf(original)

        suidCalculator.findShortestUniqueSubstring(original, input)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldThrowIllegalArgumentExceptionIfOriginalStringIsSubstringOfOneInTheList() {
        val original = "kotlin"
        val input = listOf("kotlin-lang")

        suidCalculator.findShortestUniqueSubstring(original, input)
    }
}