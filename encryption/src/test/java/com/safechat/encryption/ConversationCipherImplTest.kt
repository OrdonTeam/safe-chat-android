package com.safechat.encryption

import com.safechat.conversation.Message
import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.generator.newSymmetricKey
import org.junit.Assert
import org.junit.Test
import rx.observers.TestSubscriber

class ConversationCipherImplTest {

    val symmetricKeyBase64 = KeysBase64Cipher.toBase64String(newSymmetricKey())

    val cipher = ConversationCipherImpl()
    val subscriber = TestSubscriber<Message>()

    @Test
    fun shouldEncryptAndDecryptWithSymmetricKey() {
        val message = Message("message", true)
        cipher.encryptMessage(symmetricKeyBase64, message)
                .flatMap { cipher.decryptMessages(symmetricKeyBase64, listOf(it)) }
                .map { it.first() }
                .subscribe(subscriber)
        subscriber.assertValue(message)
    }

    @Test
    fun encryptionShouldReturnDifferentMessage() {
        val message = Message("message", true)
        cipher.encryptMessage(symmetricKeyBase64, message)
                .subscribe(subscriber)
        val encryptedMessage = subscriber.onNextEvents.first()
        Assert.assertNotEquals(message, encryptedMessage)
    }
}
