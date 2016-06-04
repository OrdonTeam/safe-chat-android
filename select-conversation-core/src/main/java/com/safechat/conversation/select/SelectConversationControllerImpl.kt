package com.safechat.conversation.select

class SelectConversationControllerImpl(val service: UsersService, val view: SelectConversationView) : SelectConversationController {

    private var users: List<User> = emptyList()
    private var query: String = ""

    override fun onCreate() {
        service.getUsers().subscribe(onSuccess, {})
    }

    val onSuccess: (List<User>) -> Unit = {
        this.users = it
        showFilteredUsers()
    }

    override fun onQuery(query: String) {
        this.query = query
        showFilteredUsers()
    }

    private fun showFilteredUsers() {
        view.showUsers(users.filter { it.rsa.contains(query) })
    }
}