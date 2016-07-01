package com.safechat.firebase.users

import com.safechat.conversation.create.User
import com.safechat.conversation.create.CreateConversationService


class CreateConversationServiceImpl : CreateConversationService {
    override fun getUsers() = getUsersList().map { it.map { User(it.rsa) } }
}