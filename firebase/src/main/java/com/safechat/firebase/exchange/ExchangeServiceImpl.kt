package com.safechat.firebase.exchange

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyService
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyService
import rx.Observable

class ExchangeServiceImpl : PostSymmetricKeyService, RetrieveSymmetricKeyService {

    override fun postSymmetricKey(myPublicKey: String, otherPublicKey: String, encryptedSymmetricKey: String): Observable<Unit> {
        throw UnsupportedOperationException()
    }

    override fun retrieveSymmetricKey(myPublicKey: String, otherPublicKey: String): Observable<String?> {
        return Observable.just(null)
    }
}
