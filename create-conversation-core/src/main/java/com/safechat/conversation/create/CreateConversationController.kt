package com.safechat.conversation.create

interface CreateConversationController {
    fun onCreate()

    fun onCreateConversationButtonClick(suid: String)
}