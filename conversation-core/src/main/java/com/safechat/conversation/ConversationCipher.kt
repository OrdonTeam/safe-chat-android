package com.safechat.conversation

import rx.Observable

interface ConversationCipher {
    fun decryptMessages(symmetricKey: String, encryptedMessages: List<Message>): Observable<List<Message>>

    fun encryptMessage(symmetricKey: String, message: Message): Observable<Message>
}