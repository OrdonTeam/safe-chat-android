package com.safechat.conversation.select

class SelectConversationControllerImpl(
        val service: ConversationListService,
        val view: SelectConversationView) : SelectConversationController {

    override fun onCreate() {
        service.getConversations()
                .subscribe(onSuccess, {})
    }

    val onSuccess: (List<UserRsaConversation>) -> Unit = {
        view.showUsers(it)
    }
}