package com.safechat.conversation.create

interface CreateConversationView {
    fun showShortestUniqueId(suid: String)

    fun showError()

    fun showUserNotFound()

    fun showUserFoundScreen(rsa: String)
}