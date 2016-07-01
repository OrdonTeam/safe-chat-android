package com.safechat.conversation.create

import rx.Observable

interface CreateConversationService {

    fun getUsers(): Observable<List<User>>
}