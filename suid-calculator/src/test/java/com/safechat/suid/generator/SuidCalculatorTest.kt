package com.safechat.suid.generator

import org.junit.Assert.assertEquals
import org.junit.Test

class SuidCalculatorTest {

    val suidCalculator = SuidCalculatorImpl()

    @Test
    fun shouldGenerateAllSubstringsLongerThanTwo() {
        val input = "kasper"

        assertEquals(setOf("kasper", "kaspe", "kasp", "kas",
                "asper", "aspe", "asp",
                "sper", "spe",
                "per"), suidCalculator.generateSubstrings(input).toSet())
    }

    @Test
    fun shouldFindShortestUniqueSubstring() {
        val input = listOf("kasper", "kas2per")

        assertEquals("mas", suidCalculator.findShortestUniqueSubstring("masper", input))
    }
}