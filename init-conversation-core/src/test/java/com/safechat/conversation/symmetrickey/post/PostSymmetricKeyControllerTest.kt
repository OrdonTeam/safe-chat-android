package com.safechat.conversation.symmetrickey.post

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.observers.TestSubscriber

class PostSymmetricKeyControllerTest {

    val encryptor = mock<PostSymmetricKeyEncryptor>()
    val controller = PostSymmetricKeyControllerImpl(encryptor)
    val subscriber = TestSubscriber<Unit>()

    @Before
    fun setUp() {
        stubGenerator()
    }

    @Test
    fun shouldGenerateKey() {
        startController()
        verify(encryptor).generateSymmetricKey()
    }

    private fun startController() {
        controller.postKey("otherPublicKey").subscribe(subscriber)
    }

    private fun stubGenerator() {
        whenever(encryptor.generateSymmetricKey()).thenReturn(Observable.just("newSymmetricKey"))
    }
}