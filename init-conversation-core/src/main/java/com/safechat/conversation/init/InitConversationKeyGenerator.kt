package com.safechat.conversation.init

import rx.Observable
import javax.crypto.SecretKey

interface InitConversationKeyGenerator {

    fun generateSymmetricKey(): Observable<SecretKey>
}