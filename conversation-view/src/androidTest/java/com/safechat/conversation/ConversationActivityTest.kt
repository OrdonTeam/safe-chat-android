package com.safechat.conversation

import android.support.test.InstrumentationRegistry
import com.safechat.conversation.ConversationActivity.Companion.activityIntent
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class ConversationActivityTest {

    val controller = Mockito.mock(ConversationController::class.java)

    @JvmField @Rule
    val rule = activityRule<ConversationActivity>(activityIntent(InstrumentationRegistry.getInstrumentation().targetContext, "otherPublicKey")) {
        ConversationActivity.conversationControllerProvider = { controller }
    }

    @Test
    fun shouldStartActivity() {
    }
}