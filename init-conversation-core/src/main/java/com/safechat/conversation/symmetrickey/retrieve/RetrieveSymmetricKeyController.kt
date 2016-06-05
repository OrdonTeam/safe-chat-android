package com.safechat.conversation.symmetrickey.retrieve

import rx.Observable

class RetrieveSymmetricKeyController {
    fun retrieveKey(rsa: String): Observable<Unit> {
        return Observable.just(Unit)
    }
}