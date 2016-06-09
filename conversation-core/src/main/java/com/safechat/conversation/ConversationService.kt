package com.safechat.conversation

import rx.Observable

interface ConversationService {
    fun listenForMessages(myPublicKey: String, otherPublicKey: String): Observable<List<Message>>

    fun postMessage(myPublicKey: String, otherPublicKey: String, message: Message): Observable<Unit>
}