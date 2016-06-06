package com.safechat.conversation.symmetrickey

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ExchangeSymmetricKeyControllerImplTest {

    val view = mock<ExchangeSymmetricKeyView>()
    val controller = ExchangeSymmetricKeyControllerImpl(view)

    @Test
    fun shouldReturnImmediatelyIfKeyAlreadySaved() {
        controller.onCreate()
        verify(view).complete()
    }
}