package com.safechat.conversation

import rx.Observable

interface ConversationService {
    fun listenForMessages(myPublicKey: String, otherPublicKey: String): Observable<com.safechat.message.Message>

    fun postMessage(myPublicKey: String, otherPublicKey: String, message: com.safechat.message.Message): Observable<Unit>
}