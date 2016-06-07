package com.safechat.conversation

interface ConversationView {
    fun showMessages(messages: List<Message>)

    fun showError()
}