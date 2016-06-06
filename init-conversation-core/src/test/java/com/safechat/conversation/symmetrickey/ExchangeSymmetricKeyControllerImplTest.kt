package com.safechat.conversation.symmetrickey

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class ExchangeSymmetricKeyControllerImplTest {

    val view = mock<ExchangeSymmetricKeyView>()
    val repository = mock<ExchangeSymmetricKeyRepository>()
    val controller = ExchangeSymmetricKeyControllerImpl(view, repository)

    @Test
    fun shouldReturnImmediatelyIfKeyAlreadySaved() {
        stubRepository(false)
        controller.onCreate("otherPublicKey")
        verify(view).complete()
    }

    private fun stubRepository(contains: Boolean) {
        whenever(repository.containsSymmetricKey("otherPublicKey")).thenReturn(contains)
    }
}