package com.safechat.user.profile

interface SuidCalculator {

    fun findShortestUniqueSubstring(original: String, others: List<String>): String
}