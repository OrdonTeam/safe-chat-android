package com.safechat.encryption

import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.base.KeysBase64Cipher.toBase64String
import com.safechat.encryption.generator.newKeyPair
import com.safechat.encryption.generator.newSymmetricKey
import com.safechat.encryption.symmetric.AESCipher
import org.junit.Assert
import org.junit.Test
import rx.observers.TestSubscriber

class SymmetricKeyCipherTest {

    val first = newKeyPair()
    val second = newKeyPair()
    val symmetricKey = toBase64String(newSymmetricKey())

    val cipher = SymmetricKeyCipher()
    val subscriber = TestSubscriber<String>()

    @Test
    fun shouldEncryptAndDecryptSymmetricKey() {
        cipher.encryptSymmetricKey(toBase64String(first.public), toBase64String(second.private), symmetricKey)
                .flatMap { cipher.decryptSymmetricKey(toBase64String(second.public), toBase64String(first.private), it) }
                .subscribe(subscriber)
        subscriber.assertValue(symmetricKey)
    }

    @Test
    fun encryptionShouldReturnEncodedKey() {
        cipher.encryptSymmetricKey(toBase64String(first.public), toBase64String(second.private), symmetricKey)
                .subscribe(subscriber)
        val encrypted = subscriber.onNextEvents.first()
        Assert.assertNotEquals(symmetricKey, encrypted)
    }

    @Test
    fun shouldReturnRealKey() {
        val message = "message"
        cipher.generateSymmetricKey()
                .map { KeysBase64Cipher.decodeSymmetricKey(it) }
                .flatMap { key -> AESCipher.encrypt(message.toByteArray(), key).map { key to it } }
                .flatMap { AESCipher.decrypt(it.second, it.first) }
                .map { String(it) }
                .subscribe (subscriber)
        subscriber.assertValue(message)
    }
}