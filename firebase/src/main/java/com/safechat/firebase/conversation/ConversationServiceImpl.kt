package com.safechat.firebase.conversation

import com.safechat.conversation.ConversationService
import com.safechat.message.Message
import com.safechat.firebase.users.findUserByRsa
import rx.Observable

//TODO: add real implementation
class ConversationServiceImpl : ConversationService {

    override fun listenForMessages(myPublicKey: String, otherPublicKey: String): Observable<com.safechat.message.Message> {
        return findUserByRsa(otherPublicKey)
                .flatMap { getPreviousMessagesWithUid(it.uid) }
    }

    override fun postMessage(myPublicKey: String, otherPublicKey: String, message: com.safechat.message.Message): Observable<Unit> {
        return findUserByRsa(otherPublicKey)
                .flatMap { postMessageToUid(it.uid, message) }
    }
}