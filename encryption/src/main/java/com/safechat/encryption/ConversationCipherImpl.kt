package com.safechat.encryption

import com.safechat.conversation.ConversationCipher
import com.safechat.conversation.Message
import rx.Observable

//TODO: add real implementation
class ConversationCipherImpl : ConversationCipher {

    override fun decryptMessages(symmetricKey: String, encryptedMessages: List<Message>): Observable<List<Message>> {
        return Observable.just(encryptedMessages)
    }

    override fun encryptMessage(symmetricKey: String, message: Message): Observable<Message> {
        return Observable.just(message)
    }
}