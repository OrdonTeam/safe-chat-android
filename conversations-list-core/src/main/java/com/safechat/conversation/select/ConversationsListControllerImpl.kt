package com.safechat.conversation.select

class ConversationsListControllerImpl(
        val service: ConversationsListService,
        val view: ConversationsListView) : ConversationsListController {

    override fun onCreate() {
        service.getConversations()
                .subscribe(onSuccess, {})
    }

    val onSuccess: (List<UserRsaConversation>) -> Unit = {
        view.showUsers(it)
    }
}