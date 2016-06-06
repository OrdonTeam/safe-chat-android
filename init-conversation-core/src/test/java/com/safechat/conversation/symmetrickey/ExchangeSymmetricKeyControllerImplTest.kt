package com.safechat.conversation.symmetrickey

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.whenever
import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyController
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_NOT_PRESENT
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
    val postController = mock<PostSymmetricKeyController>()
    val controller = ExchangeSymmetricKeyControllerImpl(view, repository, retrieveController, postController)

    @Before
    fun setUp() {
        stubRepository(false)
        stubRetrieveController(just(KEY_NOT_PRESENT))
        stubPostController(just(Unit))
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
        stubRetrieveController(just(KEY_RETRIEVED))
        startController()
        verify(view).complete()
    }

    @Test
    fun shouldShowErrorWhenRetrieveKeyFails() {
        stubRetrieveController(error(RuntimeException()))
        startController()
        verify(view).showError()
    }

    @Test
    fun shouldPostKeyWhenRetrieveReturnNoKey() {
        startController()
        verify(postController).postKey("otherPublicKey")
    }

    @Test
    fun shouldNotPostKeyWhenKeyRetrieved() {
        stubRetrieveController(just(KEY_RETRIEVED))
        startController()
        verifyZeroInteractions(postController)
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

    private fun stubPostController(postResult: Observable<Unit>) {
        whenever(postController.postKey("otherPublicKey")).thenReturn(postResult)
    }
}