package com.safechat.conversation.symmetrickey.retrieve

import rx.Observable

interface RetrieveSymmetricKeyController {
    fun retrieveKey(otherPublicKey: String): Observable<RetrieveSymmetricKeyController.RetrieveResult>

    enum class RetrieveResult {
        KEY_NOT_PRESENT, KEY_RETRIEVED
    }
}