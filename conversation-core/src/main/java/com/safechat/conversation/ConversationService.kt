package com.safechat.conversation

interface ConversationService {
    fun getPreviousMessages(myPublicKey: String, otherPublicKey: String)
}