package com.safechat.conversation.symmetrickey.post

import rx.Observable

interface PostSymmetricKeyController {
    fun postKey(otherPublicKey: String): Observable<Unit>
}