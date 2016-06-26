package com.safechat.user.profile

import rx.Observable

interface UsersService {
    fun getUsers(): Observable<List<User>>
}