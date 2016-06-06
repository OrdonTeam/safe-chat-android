package com.safechat.conversation.symmetrickey

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_RETRIEVED
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Observable.error
import rx.Observable.just

class ExchangeSymmetricKeyControllerImplTest {

    val view = mock<ExchangeSymmetricKeyView>()
    val repository = mock<ExchangeSymmetricKeyRepository>()
    val retrieveController = mock<RetrieveSymmetricKeyController>()
    val controller = ExchangeSymmetricKeyControllerImpl(view, repository, retrieveController)

    @Before
    fun setUp() {
        stubRepository(false)
        stubRetrieveController(just(KEY_RETRIEVED))
    }

    @Test
    fun shouldReturnImmediatelyIfKeyAlreadySaved() {
        stubRepository(true)
        startController()
        verifyZeroInteractions(retrieveController)
        verify(view).complete()
    }

    @Test
    fun shouldRetrieveKey() {
        startController()
        verify(view).complete()
    }

    @Test
    fun shouldShowErrorWhenRetrieveKeyFails() {
        stubRetrieveController(error(RuntimeException()))
        startController()
        verify(view).showError()
    }

    private fun startController() {
        controller.onCreate("otherPublicKey")
    }

    private fun stubRepository(contains: Boolean) {
        whenever(repository.containsSymmetricKey("otherPublicKey")).thenReturn(contains)
    }

    private fun stubRetrieveController(retrieveResult: Observable<RetrieveResult>) {
        whenever(retrieveController.retrieveKey("otherPublicKey")).thenReturn(retrieveResult)
    }
}