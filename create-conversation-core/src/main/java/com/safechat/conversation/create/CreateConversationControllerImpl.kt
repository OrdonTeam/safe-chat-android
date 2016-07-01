package com.safechat.conversation.create

class CreateConversationControllerImpl(val service: CreateConversationService,
                                       val repository: CreateConversationRepository,
                                       val suidCalculator: SuidCalculator,
                                       val view: CreateConversationView) : CreateConversationController {
    override fun onCreate() {
        val myPublicKey = repository.getPublicKeyString()
        service.getUsers()
                .map { it.filterNot { it.rsa == myPublicKey } }
                .map { suidCalculator.findShortestUniqueSubstring(myPublicKey, it.map { it.rsa }) }
                .subscribe(onSuccess, { view.showError() })
    }

    val onSuccess: (String) -> Unit = {
        view.showShortestUniqueId(it)
    }
}