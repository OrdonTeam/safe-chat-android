package com.safechat.conversation.select

import rx.Observable

interface UsersService {

    fun getUsers() : Observable<List<User>>
}