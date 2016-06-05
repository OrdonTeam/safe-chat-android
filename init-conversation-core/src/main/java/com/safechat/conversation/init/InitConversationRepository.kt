package com.safechat.conversation.init

interface InitConversationRepository {
    fun containsSavedSymmetricKey(rsa: String): Boolean
}