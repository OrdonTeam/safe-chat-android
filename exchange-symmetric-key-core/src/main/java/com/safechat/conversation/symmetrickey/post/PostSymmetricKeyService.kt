package com.safechat.conversation.symmetrickey.post

import rx.Observable

interface PostSymmetricKeyService {
    fun postSymmetricKey(myPublicKey: String, otherPublicKey: String, encryptedSymmetricKey: String): Observable<Unit>
}