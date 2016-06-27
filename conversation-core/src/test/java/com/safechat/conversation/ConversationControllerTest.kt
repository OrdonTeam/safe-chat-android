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

    val encryptedMessages = listOf(Message("encrypted_text", false, false, 1466490821381))
    val decryptedMessages = listOf(Message("decrypted_text", false, false, 1466490821384))
    val newMessage = Message("new_message", true, false, 1466490821390)
    val newEncryptedMessage = Message("new_encrypted_message", true, false, 1466490821390)

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

    @Test
    fun shouldUnsubscribe() {
        val unsubscribeVerifier = UnsubscribeVerifier.newUnsubscribeVerifier<List<Message>>()
        whenever(service.listenForMessages("myPublicKey", "otherPublicKey")).thenReturn(unsubscribeVerifier.observable)
        startController()
        controller.onDestroy()
        unsubscribeVerifier.assertWasUnsubscribed()
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
        whenever(service.listenForMessages("myPublicKey", "otherPublicKey")).thenReturn(messages)
        whenever(service.postMessage("myPublicKey", "otherPublicKey", newEncryptedMessage)).thenReturn(just(Unit))
    }

    private fun stubCipher(messages: Observable<List<Message>>) {
        whenever(cipher.decryptMessages("symmetricKey", encryptedMessages)).thenReturn(messages)
        whenever(cipher.encryptMessage("symmetricKey", newMessage)).thenReturn(just(newEncryptedMessage))
    }
}