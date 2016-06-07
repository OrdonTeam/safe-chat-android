package com.safechat.conversation.symmetrickey

import android.support.test.InstrumentationRegistry
import com.safechat.conversation.symmetrickey.ExchangeSymmetricKeyActivity.Companion.activityIntent
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class ExchangeSymmetricKeyActivityTest {

    var savedPublicKey: String? = null
    val controller: ExchangeSymmetricKeyController = object : ExchangeSymmetricKeyController {
        override fun onCreate(otherPublicKey: String) {
            savedPublicKey = otherPublicKey
        }
    }

    @JvmField @Rule
    val rule = activityRule<ExchangeSymmetricKeyActivity>(activityIntent(InstrumentationRegistry.getInstrumentation().targetContext, "otherPublicKey")) {
        ExchangeSymmetricKeyActivity.exchangeSymmetricKeyControllerProvider = { controller }
    }

    @Test
    fun shouldStartSelectConversationActivity() {
        Assert.assertEquals("otherPublicKey", savedPublicKey)
    }
}