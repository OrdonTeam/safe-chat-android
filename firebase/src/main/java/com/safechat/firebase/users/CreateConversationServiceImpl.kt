package com.safechat.firebase.users

import com.safechat.conversation.create.CreateConversationService
import com.safechat.conversation.create.User


class CreateConversationServiceImpl : CreateConversationService {
    override fun getUsers() = getUsersList().map { it.map { User(it.rsa) } }
}