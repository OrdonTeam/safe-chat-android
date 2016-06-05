package com.safechat.conversation.init

import rx.Observable

interface InitConversationService {
    fun getEncryptedSymmetricKey(rsa: String): Observable<String?>
}