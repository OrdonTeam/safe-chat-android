package com.safechat.firebase.conversations

import com.safechat.conversation.select.ConversationListService
import com.safechat.conversation.select.UserRsaConversation
import com.safechat.firebase.auth.myUid
import com.safechat.firebase.users.UserUidAndRsa
import com.safechat.firebase.users.getUsersList
import rx.Observable

class ConversationListServiceImpl : ConversationListService {
    override fun getConversations(): Observable<List<UserRsaConversation>> {
        return getUsersList().zipWith(getConversationsList(myUid()), { users, conversations ->
            conversations.map { conversation ->
                createUserRsaConversation(conversation, users)
            }
        })
    }

    private fun createUserRsaConversation(conversation: UserConversation, users: List<UserUidAndRsa>) =
            UserRsaConversation(
                    rsa = users.first { it.uid == conversation.userUid }.rsa,
                    lastMessage = conversation.lastMessage)
}