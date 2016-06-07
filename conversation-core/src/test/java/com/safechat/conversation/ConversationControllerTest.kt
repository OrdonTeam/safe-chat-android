package com.safechat.conversation

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Observable.error
import rx.Observable.just

class ConversationControllerTest {

    val view = mock<ConversationView>()
    val cipher = mock<ConversationCipher>()
    val repository = mock<ConversationRepository>()
    val service = mock<ConversationService>()
    val controller = ConversationControllerImpl(service, repository, cipher, view)

    val encryptedMessages = listOf(Message("encrypted_text", false))
    val decryptedMessages = listOf(Message("decrypted_text", false))
    val newMessage = Message("new_message", true)
    val newEncryptedMessage = Message("new_encrypted_message", true)

    @Before
    fun setUp() {
        stubRepository()
        stubService(just(encryptedMessages))
        stubCipher(just(decryptedMessages))
    }

    @Test
    fun shouldShowDecryptedMessages() {
        startController()
        verify(view).showMessages(decryptedMessages)
    }

    @Test
    fun shouldShowErrorWhenServiceFails() {
        stubService(error(RuntimeException()))
        startController()
        verify(view).showError()
    }

    @Test
    fun shouldShowErrorWhenCipherFails() {
        stubCipher(error(RuntimeException()))
        startController()
        verify(view).showError()
    }

    @Test
    fun shouldPostNewMessage() {
        sendNewMessage()
        verify(service).postMessage("myPublicKey", "otherPublicKey", newEncryptedMessage)
    }

    private fun startController() {
        controller.onCreated("otherPublicKey")
    }

    private fun sendNewMessage() {
        controller.onNewMessage("otherPublicKey", newMessage)
    }

    private fun stubRepository() {
        whenever(repository.getPublicKeyString()).thenReturn("myPublicKey")
        whenever(repository.getDecryptedSymmetricKey("otherPublicKey")).thenReturn("symmetricKey")
    }

    private fun stubService(messages: Observable<List<Message>>) {
        whenever(service.getPreviousMessages("myPublicKey", "otherPublicKey")).thenReturn(messages)
        whenever(service.postMessage("myPublicKey", "otherPublicKey", newEncryptedMessage)).thenReturn(just(Unit))
    }

    private fun stubCipher(messages: Observable<List<Message>>) {
        whenever(cipher.decryptMessages("symmetricKey", encryptedMessages)).thenReturn(messages)
        whenever(cipher.encryptMessage("symmetricKey", newMessage)).thenReturn(just(newEncryptedMessage))
    }
}