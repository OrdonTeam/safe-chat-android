package com.safechat.conversation.symmetrickey.retrieve

import rx.Observable

interface RetrieveSymmetricKeyService {
    fun retrieveSymmetricKey(myPublicKey: String, otherPublicKey: String): Observable<String?>
}