package com.safechat.suid.generator

import com.safechat.user.profile.SuidCalculator

class SuidCalculatorImpl : SuidCalculator {

    val MINIMAL_UNIQUE_LENGTH = 2

    override fun findShortestUniqueSubstring(original: String, others: List<String>): String {
        if (others.contains(original)) {
            throw IllegalArgumentException()
        }
        return findRecursively(MINIMAL_UNIQUE_LENGTH, original, others)
    }

    private fun findRecursively(length: Int, original: String, others: List<String>): String {
        return findUniqueSubstringOfLength(length, original, others) ?: findRecursively(length + 1, original, others)
    }

    private fun findUniqueSubstringOfLength(length: Int, original: String, others: List<String>): String? {
        return original.substringsForLength(length).firstOrNull { isNotContainedByAnyElementOfList(it, others) }
    }

    private fun isNotContainedByAnyElementOfList(substring: String, list: List<String>) =
            list.none {
                it.contains(substring)
            }

    private fun String.substringsForLength(length: Int): List<String> {
        val maximalStartIndex = this.length - length
        return (0..maximalStartIndex).map {
            substring(it, it + length)
        }
    }
}