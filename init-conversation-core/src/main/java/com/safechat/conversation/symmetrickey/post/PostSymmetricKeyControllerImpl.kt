package com.safechat.conversation.symmetrickey.post

import rx.Observable

class PostSymmetricKeyControllerImpl(
        val encryptor: PostSymmetricKeyEncryptor,
        val repository: PostSymmetricKeyRepository) : PostSymmetricKeyController {

    override fun postKey(otherPublicKey: String): Observable<Unit> {
        return encryptor.generateSymmetricKey()
                .flatMap { newSymmetricKey ->
                    encryptor.encryptSymmetricKey(otherPublicKey, repository.getPrivateKeyString(), newSymmetricKey)
                            .map { newSymmetricKey }
                }
                .map { repository.saveDecryptedSymmetricKey(otherPublicKey, it) }
    }
}