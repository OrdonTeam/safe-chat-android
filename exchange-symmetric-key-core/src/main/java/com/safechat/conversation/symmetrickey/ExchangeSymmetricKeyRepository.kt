package com.safechat.conversation.symmetrickey

interface ExchangeSymmetricKeyRepository {
    fun containsSymmetricKey(otherPublicKey: String): Boolean
}