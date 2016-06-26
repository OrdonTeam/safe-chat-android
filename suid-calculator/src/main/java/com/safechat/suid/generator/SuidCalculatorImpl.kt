package com.safechat.suid.generator

import com.safechat.user.profile.SuidCalculator

class SuidCalculatorImpl : SuidCalculator {

    override fun findShortestUniqueSubstring(original: String, others: List<String>): String {
        val MINIMAL_UNIQUE_LENGTH = 2
        for (length in MINIMAL_UNIQUE_LENGTH..original.length) {
            val substrings = original.substringsForLength(length)
            val result = substrings.firstOrNull { isNotContainedByAnyElementOfList(it, others) }
            if (result != null) {
                if (!original.contains(result)) {
                    throw RuntimeException()
                }
                return result
            }
        }
        throw IllegalStateException();
    }

    private fun isNotContainedByAnyElementOfList(substring: String, list: List<String>) =
            list.any {
                it.contains(substring)
            }.not()

    private fun String.substringsForLength(length: Int) =
            mapIndexed { index, letter ->
                drop(index)
            }.filter {
                it.length >= length
            }.map {
                it.take(length)
            }
}