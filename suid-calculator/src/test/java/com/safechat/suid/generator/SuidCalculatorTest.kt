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
}