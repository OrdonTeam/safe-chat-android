package com.safechat.conversation.symmetrickey

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController
import org.junit.Before
import org.junit.Test

class ExchangeSymmetricKeyControllerImplTest {

    val view = mock<ExchangeSymmetricKeyView>()
    val repository = mock<ExchangeSymmetricKeyRepository>()
    val retrieveController = mock<RetrieveSymmetricKeyController>()
    val controller = ExchangeSymmetricKeyControllerImpl(view, repository, retrieveController)

    @Before
    fun setUp() {
        stubRepository(true)
    }

    @Test
    fun shouldReturnImmediatelyIfKeyAlreadySaved() {
        stubRepository(false)
        startController()
        verify(view).complete()
    }

    @Test
    fun shouldRetrieveKey() {
        startController()
        verify(retrieveController).retrieveKey("otherPublicKey")
    }

    private fun startController() {
        controller.onCreate("otherPublicKey")
    }

    private fun stubRepository(contains: Boolean) {
        whenever(repository.containsSymmetricKey("otherPublicKey")).thenReturn(contains)
    }
}