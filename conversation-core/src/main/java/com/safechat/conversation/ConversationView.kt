package com.safechat.conversation

interface ConversationView {
    fun showMessages(decryptedMessages: List<Message>)
}