package com.safechat.conversation

interface ConversationController {
    fun onCreated(otherPublicKey: String)
}