package com.safechat.conversation.select

class SelectConversationController(val service: UsersService, val view: SelectConversationView) {

    private var users: List<User> = emptyList()

    fun onCreate() {
        service.getUsers().subscribe(onSuccess, {})
    }

    val onSuccess: (List<User>) -> Unit = {
        this.users = it
        view.showUsers(it)
    }

    fun onQuery(query: String) {
        view.showUsers(users.filter { it.rsa.contains(query) })
    }
}