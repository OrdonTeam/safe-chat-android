package com.safechat.conversation.symmetrickey

class ExchangeSymmetricKeyControllerImpl(
        val view: ExchangeSymmetricKeyView,
        val repository: ExchangeSymmetricKeyRepository) : ExchangeSymmetricKeyController {

    override fun onCreate(otherPublicKey: String) {
        view.complete()
    }
}