package com.safechat.firebase.exchange

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyService
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyService
import com.safechat.firebase.users.findUserByRsa
import rx.Observable

class ExchangeServiceImpl : PostSymmetricKeyService, RetrieveSymmetricKeyService {

    override fun postSymmetricKey(myPublicKey: String, otherPublicKey: String, encryptedSymmetricKey: String): Observable<Unit> {
        return findUserByRsa(otherPublicKey)
                .flatMap { postSymmetricKeyToUserUid(myPublicKey, it.uid, encryptedSymmetricKey) }
    }

    override fun retrieveSymmetricKey(myPublicKey: String, otherPublicKey: String): Observable<String?> {
        return Observable.just(null)
    }
}
