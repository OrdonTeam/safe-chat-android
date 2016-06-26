package com.safechat.suid.generator

import com.safechat.user.profile.SuidCalculator

class SuidCalculatorImpl : SuidCalculator {

    override fun findShortestUniqueSubstring(original: String, others: List<String>) =
            generateSubstrings(original)
                    .first { substring ->
                        isNotContainedByAnyElementOfList(substring, others)
                    }

    private fun isNotContainedByAnyElementOfList(substring: String, others: List<String>) =
            others.any {
                it.contains(substring)
            }.not()

    fun generateSubstrings(input: String) =
            input.mapIndexed { index, letter ->
                input.substring(0, input.length - index)
            }.flatMap { text ->
                text.mapIndexed { index, letter ->
                    text.substring(index, text.length)
                }
            }.toSet()
                    .filter { it.length > 2 }
                    .sortedBy { it.length }

}