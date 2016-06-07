package com.safechat.conversation

interface ConversationRepository {
    fun getPublicKeyString(): String

    fun getDecryptedSymmetricKey(otherPublicKey: String): String
}