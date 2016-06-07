package com.safechat.conversation

interface ConversationRepository {
    fun getPublicKeyString(): String
}