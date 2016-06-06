package com.safechat.conversation.symmetrickey

import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController

class ExchangeSymmetricKeyControllerImpl(
        val view: ExchangeSymmetricKeyView,
        val repository: ExchangeSymmetricKeyRepository,
        val retrieveController: RetrieveSymmetricKeyController) : ExchangeSymmetricKeyController {

    override fun onCreate(otherPublicKey: String) {
        if (repository.containsSymmetricKey(otherPublicKey)) {
            view.complete()
        } else {
            retrieveController.retrieveKey(otherPublicKey)
                    .subscribe({
                        view.complete()
                    }, {
                        view.showError()
                    })
        }
    }
}