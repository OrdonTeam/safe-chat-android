package com.safechat.conversation.symmetrickey.post

import rx.Observable

class PostSymmetricKeyControllerImpl(
        val encryptor: PostSymmetricKeyEncryptor,
        val repository: PostSymmetricKeyRepository,
        val service: PostSymmetricKeyService) : PostSymmetricKeyController {

    override fun postKey(otherPublicKey: String): Observable<Unit> {
        return encryptor.generateSymmetricKey()
                .flatMap { newKey ->
                    encryptor.encryptSymmetricKey(otherPublicKey, repository.getPrivateKeyString(), newKey)
                            .flatMap { service.postSymmetricKey(repository.getPublicKeyString(), otherPublicKey, it) }
                            .map { newKey }
                }
                .map { repository.saveDecryptedSymmetricKey(otherPublicKey, it) }
    }
}