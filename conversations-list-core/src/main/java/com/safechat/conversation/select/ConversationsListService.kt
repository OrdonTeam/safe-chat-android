package com.safechat.conversation.select;

import rx.Observable

interface ConversationsListService {
    fun getConversations(): Observable<List<UserRsaConversation>>
}
