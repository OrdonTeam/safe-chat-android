package com.safechat.conversation.symmetrickey

import android.support.test.InstrumentationRegistry
import com.safechat.conversation.symmetrickey.ExchangeSymmetricKeyActivity.Companion.activityIntent
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class ExchangeSymmetricKeyActivityTest {

    val controller = Mockito.mock(ExchangeSymmetricKeyController::class.java)

    @JvmField @Rule
    val rule = activityRule<ExchangeSymmetricKeyActivity>(activityIntent(InstrumentationRegistry.getInstrumentation().targetContext, "otherPublicKey")) {
        ExchangeSymmetricKeyActivity.exchangeSymmetricKeyControllerProvider = { controller }
    }

    @Test
    fun shouldStartSelectConversationActivity() {
        Mockito.verify(controller).onCreate("otherPublicKey")
    }
}