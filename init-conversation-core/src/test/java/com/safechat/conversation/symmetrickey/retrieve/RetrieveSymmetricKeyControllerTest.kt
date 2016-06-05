package com.safechat.conversation.symmetrickey.retrieve

import org.junit.Test
import rx.observers.TestSubscriber

class RetrieveSymmetricKeyControllerTest {

    val controller = RetrieveSymmetricKeyController()
    val subscriber = TestSubscriber<Unit>()

    @Test
    fun shouldReturnKeyNotPresent() {
        controller.retrieveKey("rsa").subscribe(subscriber)
        subscriber.assertValue(Unit)
    }
}