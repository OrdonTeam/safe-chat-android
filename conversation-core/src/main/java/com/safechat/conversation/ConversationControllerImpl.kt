package com.safechat.conversation

class ConversationControllerImpl(
        val service: ConversationService) : ConversationController {

    override fun onCreated(otherPublicKey: String) {
        service.getPreviousMessages("myPublicKey", otherPublicKey)
    }
}