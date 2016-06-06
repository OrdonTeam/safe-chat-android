package com.safechat.conversation.symmetrickey

class ExchangeSymmetricKeyControllerImpl(
        val view: ExchangeSymmetricKeyView) : ExchangeSymmetricKeyController {

    override fun onCreate() {
        view.complete()
    }
}