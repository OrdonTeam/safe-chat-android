package com.safechat.conversation

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class ConversationControllerTest {

    val service = mock<ConversationService>()
    val controller = ConversationControllerImpl(service)

    @Test
    fun shouldDownloadPreviousMessages() {
        startController()
        verify(service).getPreviousMessages("myPublicKey", "otherPublicKey")
    }

    private fun startController() {
        controller.onCreated("otherPublicKey")
    }
}