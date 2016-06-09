package com.safechat.firebase.exchange

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyService
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyService
import com.safechat.firebase.auth.myUid
import com.safechat.firebase.users.findUserByRsa
import rx.Observable

class ExchangeServiceImpl : PostSymmetricKeyService, RetrieveSymmetricKeyService {

    override fun postSymmetricKey(myPublicKey: String, otherPublicKey: String, encryptedSymmetricKey: String): Observable<Unit> {
        return findUserByRsa(otherPublicKey)
                .flatMap { postSymmetricKeyToUserUid(myUid(), it.uid, encryptedSymmetricKey) }
    }

    override fun retrieveSymmetricKey(myPublicKey: String, otherPublicKey: String): Observable<String?> {
        return findUserByRsa(otherPublicKey)
                .flatMap { user ->
                    retrieveKeyFromUserUid(myUid(), user.uid)
                            .map { it to user.uid }
                            .flatMap { pair ->
                                removeKeyFromUserUid(myUid(), pair.second).map { pair.first }
                            }
                }
    }
}
