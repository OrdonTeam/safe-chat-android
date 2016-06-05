package com.safechat.conversation.symmetrickey.retrieve

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_NOT_PRESENT
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_RETRIEVED
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Observable.just
import rx.observers.TestSubscriber

class RetrieveSymmetricKeyControllerTest {

    val repository = mock<RetrieveSymmetricKeyRepository>()
    val service = mock<RetrieveSymmetricKeyService>()
    val controller = RetrieveSymmetricKeyController(service, repository)
    val subscriber = TestSubscriber<RetrieveResult>()

    @Before
    fun setUp() {
        stubRepository("myPublicKey")
        stubService(just(null))
    }

    @Test
    fun shouldReturnKeyNotPresent() {
        startController()
        subscriber.assertValue(KEY_NOT_PRESENT)
    }

    @Test
    fun shouldReturnKeyRetrieved() {
        stubService(just("encryptedSymmetricKey"))
        startController()
        subscriber.assertValue(KEY_RETRIEVED)
    }

    @Test
    fun shouldUseSelfPublicKeyInCall() {
        startController()
        subscriber.assertValue(KEY_NOT_PRESENT)
    }

    private fun startController() {
        controller.retrieveKey("otherPublicKey").subscribe(subscriber)
    }

    private fun stubRepository(myPublicKey: String) {
        whenever(repository.getPublicKeyString()).thenReturn(myPublicKey)
    }

    private fun stubService(encryptedSymmetricKey: Observable<String?>) {
        whenever(service.retrieveSymmetricKey("myPublicKey", "otherPublicKey")).thenReturn(encryptedSymmetricKey)
    }
}