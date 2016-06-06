package com.safechat.conversation.symmetrickey

import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController

class ExchangeSymmetricKeyControllerImpl(
        val view: ExchangeSymmetricKeyView,
        val repository: ExchangeSymmetricKeyRepository,
        val retrieveController: RetrieveSymmetricKeyController) : ExchangeSymmetricKeyController {

    override fun onCreate(otherPublicKey: String) {
        view.complete()
        retrieveController.retrieveKey(otherPublicKey)
    }
}