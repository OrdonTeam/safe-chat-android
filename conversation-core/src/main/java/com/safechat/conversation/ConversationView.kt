package com.safechat.conversation

interface ConversationView {
    fun showMessages(messages: List<com.safechat.message.Message>)

    fun showError()

    fun close()
}