package com.safechat.conversation.symmetrickey

interface ExchangeSymmetricKeyView {
    fun complete()

    fun showError()

    fun showLoader()

    fun hideLoader()
}