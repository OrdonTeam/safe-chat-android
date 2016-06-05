package com.safechat.conversation.symmetrickey.post

interface PostSymmetricKeyRepository {

    fun saveDecryptedSymmetricKey(otherPublicKey: String, decryptedSymmetricKey: String)

    fun getPublicKeyString(): String

    fun getPrivateKeyString(): String
}