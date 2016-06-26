package com.safechat.firebase.users

import com.safechat.user.profile.User
import com.safechat.user.profile.UsersService

class UserServiceImpl2 : UsersService {
    override fun getUsers() = getUsersList().map { it.map { User(it.rsa) } }
}