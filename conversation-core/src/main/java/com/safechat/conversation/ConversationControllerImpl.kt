package com.safechat.conversation

class ConversationControllerImpl(
        val service: ConversationService,
        val repository: ConversationRepository,
        val cipher: ConversationCipher) : ConversationController {

    override fun onCreated(otherPublicKey: String) {
        service.getPreviousMessages(repository.getPublicKeyString(), otherPublicKey)
                .flatMap { cipher.decryptMessages(repository.getDecryptedSymmetricKey(otherPublicKey), it) }
                .subscribe()
    }
}