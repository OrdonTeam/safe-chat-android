package com.safechat.conversation.symmetrickey.retrieve

interface RetrieveSymmetricKeyRepository {

    fun getPublicKeyString(): String

    fun getPrivateKeyString(): String
}