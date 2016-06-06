package com.safechat.conversation.symmetrickey

interface ExchangeSymmetricKeyRepository {
    fun containsSymmetricKey(rsa: String): Boolean
}