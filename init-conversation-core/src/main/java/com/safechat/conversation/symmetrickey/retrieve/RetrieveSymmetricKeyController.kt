package com.safechat.conversation.symmetrickey.retrieve

import rx.Observable

class RetrieveSymmetricKeyController(val service: RetrieveSymmetricKeyService) {
    fun retrieveKey(otherPublicKey: String): Observable<RetrieveResult> {
        return service.retrieveSymmetricKey("", otherPublicKey).map {
            if (it == null) {
                RetrieveResult.KEY_NOT_PRESENT
            } else {
                RetrieveResult.KEY_RETRIEVED
            }
        }
    }

    enum class RetrieveResult {
        KEY_NOT_PRESENT, KEY_RETRIEVED
    }
}