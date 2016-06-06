package com.safechat.firebase.users

import com.safechat.conversation.select.User
import com.safechat.conversation.select.UsersService

class UsersServiceImpl : UsersService {
    override fun getUsers() = getUsersList().map { it.map { User(it.rsa) } }
}