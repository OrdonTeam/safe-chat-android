package com.safechat.conversation.create

interface SuidCalculator {

    fun findShortestUniqueSubstring(original: String, others: List<String>): String
}