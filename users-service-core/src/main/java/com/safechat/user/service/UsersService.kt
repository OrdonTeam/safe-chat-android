package com.safechat.user.service

import rx.Observable

interface UsersService {

    fun getUsers(): Observable<List<User>>
}