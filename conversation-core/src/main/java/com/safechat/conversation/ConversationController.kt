package com.safechat.conversation

interface ConversationController {
    fun onCreated(otherPublicKey: String)

    fun onNewMessage(otherPublicKey: String, message: Message)
}