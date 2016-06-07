package com.safechat.conversation

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

class ConversationControllerTest {

    val repository = mock<ConversationRepository>()
    val service = mock<ConversationService>()
    val controller = ConversationControllerImpl(service, repository)

    @Before
    fun setUp() {
        stubRepository()
    }

    @Test
    fun shouldReadSavedPublicKey() {
        startController()
        verify(repository).getPublicKeyString()
    }

    @Test
    fun shouldDownloadPreviousMessages() {
        startController()
        verify(service).getPreviousMessages("myPublicKey", "otherPublicKey")
    }

    private fun startController() {
        controller.onCreated("otherPublicKey")
    }

    private fun stubRepository() {
        whenever(repository.getPublicKeyString()).thenReturn("myPublicKey")
    }
}