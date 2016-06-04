package com.safechat.conversation.select

interface SelectConversationController {
    fun onCreate()
    fun onQuery(query: String)
}