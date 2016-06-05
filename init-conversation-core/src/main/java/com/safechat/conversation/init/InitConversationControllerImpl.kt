package com.safechat.conversation.init

class InitConversationControllerImpl(
        val view: InitConversationView,
        val repository: InitConversationRepository,
        val service: InitConversationService) : InitConversationController {

    override fun onCreate(rsa: String) {
        if (repository.containsSavedSymmetricKey(rsa)) {
            view.complete()
        } else {
            service.getEncryptedSymmetricKey(rsa)
                    .subscribe({ view.complete() })
        }
    }
}