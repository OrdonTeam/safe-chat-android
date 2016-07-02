package com.safechat.encryption

import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.generator.newSymmetricKey
import org.junit.Assert
import org.junit.Test
import rx.observers.TestSubscriber

class ConversationCipherImplTest {

    val symmetricKeyBase64 = KeysBase64Cipher.toBase64String(newSymmetricKey())

    val cipher = ConversationCipherImpl()
    val subscriber = TestSubscriber<com.safechat.message.Message>()

    @Test
    fun shouldEncryptAndDecryptWithSymmetricKey() {
        val message = com.safechat.message.Message("message", true, false, 1466490821381)
        cipher.encryptMessage(symmetricKeyBase64, message)
                .flatMap { cipher.decryptMessages(symmetricKeyBase64, listOf(it)) }
                .map { it.first() }
                .subscribe(subscriber)
        subscriber.assertValue(message)
    }

    @Test
    fun encryptionShouldReturnDifferentMessage() {
        val message = com.safechat.message.Message("message", true, false, 1466490821381)
        cipher.encryptMessage(symmetricKeyBase64, message)
                .subscribe(subscriber)
        val encryptedMessage = subscriber.onNextEvents.first()
        Assert.assertNotEquals(message, encryptedMessage)
    }
}
