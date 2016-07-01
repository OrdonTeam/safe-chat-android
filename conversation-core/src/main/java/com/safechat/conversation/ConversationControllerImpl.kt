package com.safechat.conversation

import rx.Subscription

class ConversationControllerImpl(
        val service: ConversationService,
        val repository: ConversationRepository,
        val cipher: ConversationCipher,
        val view: ConversationView) : ConversationController {

    var subscription: Subscription? = null

    override fun onCreated(otherPublicKey: String) {
        subscription = service.listenForMessages(repository.getPublicKeyString(), otherPublicKey)
                .map { listOf(it) }
                .flatMap { cipher.decryptMessages(repository.getDecryptedSymmetricKey(otherPublicKey), it) }
                .doOnNext { repository.saveConversationMessage(otherPublicKey, it.last()) }
                .subscribe({ view.showMessages(it) }, { view.showError() })
    }

    override fun onNewMessage(otherPublicKey: String, message: com.safechat.message.Message) {
        repository.saveConversationMessage(otherPublicKey, message)
        cipher.encryptMessage(repository.getDecryptedSymmetricKey(otherPublicKey), message)
                .flatMap { service.postMessage(repository.getPublicKeyString(), otherPublicKey, it) }
                .subscribe({}, { view.showError() })
    }

    override fun onDestroy() {
        subscription?.unsubscribe()
    }
}