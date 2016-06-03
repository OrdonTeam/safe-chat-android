package com.safechat.conversation.select

class SelectConversationController(val service: UsersService, val view: SelectConversationView) {

    private var users: List<User> = emptyList()
    private var query: String = ""

    fun onCreate() {
        service.getUsers().subscribe(onSuccess, {})
    }

    val onSuccess: (List<User>) -> Unit = {
        this.users = it
        showFilteredUsers()
    }

    fun onQuery(query: String) {
        this.query = query
        showFilteredUsers()
    }

    private fun showFilteredUsers() {
        view.showUsers(users.filter { it.rsa.contains(query) })
    }
}