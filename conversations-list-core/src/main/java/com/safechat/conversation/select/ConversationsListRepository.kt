package com.safechat.conversation.select

import com.safechat.message.Message

interface ConversationsListRepository {
    fun getConversationsMessages(): Map<String, Message>
}

