package com.safechat.conversation.init

class InitConversationControllerImpl(val view: InitConversationView, val repository: InitConversationRepository) : InitConversationController {
    override fun onCreate(rsa: String) {
        view.complete()
    }
}