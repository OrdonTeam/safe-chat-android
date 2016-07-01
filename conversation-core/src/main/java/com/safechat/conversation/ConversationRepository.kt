package com.safechat.conversation

interface ConversationRepository {
    fun getPublicKeyString(): String

    fun getDecryptedSymmetricKey(otherPublicKey: String): String

    fun saveConversationMessage(otherPublicKey: String, message: com.safechat.message.Message)
}