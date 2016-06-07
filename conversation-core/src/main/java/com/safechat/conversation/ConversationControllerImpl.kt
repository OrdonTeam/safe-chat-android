package com.safechat.conversation

class ConversationControllerImpl(
        val service: ConversationService,
        val repository: ConversationRepository) : ConversationController {

    override fun onCreated(otherPublicKey: String) {
        service.getPreviousMessages(repository.getPublicKeyString(), otherPublicKey)
    }
}