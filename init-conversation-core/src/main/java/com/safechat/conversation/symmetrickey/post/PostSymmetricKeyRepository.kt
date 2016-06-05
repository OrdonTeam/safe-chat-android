package com.safechat.conversation.symmetrickey.post

interface PostSymmetricKeyRepository {

    fun saveDecryptedSymmetricKey(otherPublicKey: String, decryptedSymmetricKey: String)
}