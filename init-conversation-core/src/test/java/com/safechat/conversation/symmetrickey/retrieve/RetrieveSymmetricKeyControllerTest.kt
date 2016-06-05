package com.safechat.conversation.symmetrickey.retrieve

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_NOT_PRESENT
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_RETRIEVED
import org.junit.Test
import rx.Observable
import rx.Observable.just
import rx.observers.TestSubscriber

class RetrieveSymmetricKeyControllerTest {

    val service = mock<RetrieveSymmetricKeyService>()
    val controller = RetrieveSymmetricKeyController(service)
    val subscriber = TestSubscriber<RetrieveResult>()

    @Test
    fun shouldReturnKeyNotPresent() {
        stubService(just(null))
        startController()
        subscriber.assertValue(KEY_NOT_PRESENT)
    }

    @Test
    fun shouldReturnKeyRetrieved() {
        stubService(just("encryptedSymmetricKey"))
        startController()
        subscriber.assertValue(KEY_RETRIEVED)
    }

    private fun startController() {
        controller.retrieveKey("otherPublicKey").subscribe(subscriber)
    }

    private fun stubService(encryptedSymmetricKey: Observable<String?>) {
        whenever(service.retrieveSymmetricKey(any(), any())).thenReturn(encryptedSymmetricKey)
    }
}