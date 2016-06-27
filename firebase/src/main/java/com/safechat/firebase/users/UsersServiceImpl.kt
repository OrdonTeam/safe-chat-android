package com.safechat.firebase.users

import com.safechat.user.service.User
import com.safechat.user.service.UsersService

class UsersServiceImpl : UsersService {
    override fun getUsers() = getUsersList().map { it.map { User(it.rsa) } }
}