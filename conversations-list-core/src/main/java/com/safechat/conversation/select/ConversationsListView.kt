package com.safechat.conversation.select

import com.safechat.message.Message

interface ConversationsListView {
    fun showConversations(conversations: Map<String, Message>)

    fun showEmptyConversationsPlaceholder()
}