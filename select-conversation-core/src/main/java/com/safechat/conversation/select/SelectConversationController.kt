package com.safechat.conversation.select

class SelectConversationController(val service: UsersService, val view: SelectConversationView) {
    fun onCreate() {
        service.getUsers().subscribe({ view.showUsers(it) }, {})
    }
}