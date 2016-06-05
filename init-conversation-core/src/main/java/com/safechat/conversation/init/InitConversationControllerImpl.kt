package com.safechat.conversation.init

class InitConversationControllerImpl(
        val view: InitConversationView,
        val repository: InitConversationRepository,
        val service: InitConversationService,
        val keyGenerator: InitConversationKeyGenerator) : InitConversationController {

    override fun onCreate(rsa: String) {
        if (repository.containsSavedSymmetricKey(rsa)) {
            view.complete()
        } else {
            service.getEncryptedSymmetricKey(rsa)
                    .subscribe(onEncryptedKey, { view.showError() })
        }
    }

    val onEncryptedKey: (String?) -> Unit = {
        if (it == null) {
            keyGenerator.generateSymmetricKey()
                    .subscribe({ view.complete() })
        } else {
            view.complete()
        }
    }
}