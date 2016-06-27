package com.safechat.conversation.select

import com.safechat.user.service.User
import com.safechat.user.service.UsersService

class SelectConversationControllerImpl(
        val service: UsersService,
        val view: SelectConversationView,
        val repository: SelectConversationRepository) : SelectConversationController {

    private var users: List<User> = emptyList()
    private var query: String = ""

    override fun onCreate() {
        service.getUsers()
                .map { it.filterNot { it.rsa == repository.getPublicKeyString() } }
                .subscribe(onSuccess, {})
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