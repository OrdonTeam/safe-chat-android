package com.safechat.conversation.symmetrickey.post

import rx.Observable

interface PostSymmetricKeyEncryptor {
    fun generateSymmetricKey(): Observable<String>

    fun encryptSymmetricKey(otherPublicKey: String, myPrivateKey: String, newSymmetricKey: String): Observable<String>
}