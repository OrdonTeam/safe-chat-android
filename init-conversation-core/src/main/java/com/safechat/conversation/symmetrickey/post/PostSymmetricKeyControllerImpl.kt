package com.safechat.conversation.symmetrickey.post

import rx.Observable

class PostSymmetricKeyControllerImpl(
        val encryptor: PostSymmetricKeyEncryptor) : PostSymmetricKeyController {

    override fun postKey(otherPublicKey: String): Observable<Unit> {
        return encryptor.generateSymmetricKey().map { Unit }
    }
}