package com.safechat.conversation.symmetrickey.post

import rx.Observable

class PostSymmetricKeyControllerImpl(
        val encryptor: PostSymmetricKeyEncryptor,
        val repository: PostSymmetricKeyRepository,
        val service: PostSymmetricKeyService) : PostSymmetricKeyController {

    override fun postKey(otherPublicKey: String): Observable<Unit> {
        return encryptor.generateSymmetricKey()
                .doOnNext {
                    encryptor.encryptSymmetricKey(otherPublicKey, repository.getPrivateKeyString(), it)
                            .flatMap { service.postSymmetricKey(repository.getPublicKeyString(), otherPublicKey, it) }
                }
                .map { repository.saveDecryptedSymmetricKey(otherPublicKey, it) }
    }
}