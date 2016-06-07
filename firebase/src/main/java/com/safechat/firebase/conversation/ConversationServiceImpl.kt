package com.safechat.firebase.conversation

import com.safechat.conversation.ConversationService
import com.safechat.conversation.Message
import rx.Observable

//TODO: add real implementation
class ConversationServiceImpl : ConversationService {

    override fun getPreviousMessages(myPublicKey: String, otherPublicKey: String): Observable<List<Message>> {
        return Observable.just(emptyList())
    }

    override fun postMessage(myPublicKey: String, otherPublicKey: String, message: Message): Observable<Unit> {
        return Observable.just(Unit)
    }
}