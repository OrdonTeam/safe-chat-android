package com.safechat.suid.generator

import com.safechat.user.profile.SuidCalculator

class SuidCalculatorImpl : SuidCalculator {

    override fun findShortestUniqueSubstring(original: String, others: List<String>): String {
        if (others.contains(original)) {
            throw IllegalArgumentException()
        }
        val MINIMAL_UNIQUE_LENGTH = 2
        var result = ""
        for (length in MINIMAL_UNIQUE_LENGTH..original.length) {
            val substrings = original.substringsForLength(length)
            val uniqueText = substrings.firstOrNull { isNotContainedByAnyElementOfList(it, others) }
            if (uniqueText != null) {
                result = uniqueText
                break
            }
        }
        return result
    }

    private fun isNotContainedByAnyElementOfList(substring: String, list: List<String>) =
            list.none {
                it.contains(substring)
            }

    private fun String.substringsForLength(length: Int) =
            mapIndexed { index, letter ->
                drop(index)
            }.filter {
                it.length >= length
            }.map {
                it.take(length)
            }
}