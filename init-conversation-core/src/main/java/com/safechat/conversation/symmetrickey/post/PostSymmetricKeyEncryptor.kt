package com.safechat.conversation.symmetrickey.post

import rx.Observable

interface PostSymmetricKeyEncryptor {
    fun generateSymmetricKey(): Observable<String>
}