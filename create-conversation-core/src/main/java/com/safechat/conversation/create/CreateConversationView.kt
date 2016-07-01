package com.safechat.conversation.create

interface CreateConversationView {
    fun showShortestUniqueId(suid: String)

    fun showError()
}