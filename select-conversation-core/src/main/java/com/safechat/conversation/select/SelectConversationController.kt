package com.safechat.conversation.select

class SelectConversationController(val service: UsersService) {
    fun onCreate() {
        service.getUsers()
    }
}