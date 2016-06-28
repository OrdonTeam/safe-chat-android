package com.safechat.conversation.select;

import rx.Observable

interface ConversationListService {
    fun getConversations(): Observable<List<UserRsaConversation>>
}
