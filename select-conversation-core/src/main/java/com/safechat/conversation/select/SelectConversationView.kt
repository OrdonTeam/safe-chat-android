package com.safechat.conversation.select

import com.safechat.user.service.User

interface SelectConversationView {
    fun showUsers(users: List<User>)
}