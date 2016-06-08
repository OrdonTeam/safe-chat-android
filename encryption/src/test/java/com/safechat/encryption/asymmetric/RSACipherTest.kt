package com.safechat.encryption.asymmetric

import com.safechat.encryption.asymmetric.RSACipher.decrypt
import com.safechat.encryption.asymmetric.RSACipher.encrypt
import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.generator.newKeyPair
import com.safechat.encryption.generator.newSymmetricKey
import org.junit.Test
import rx.observers.TestSubscriber

class RSACipherTest {

    val keyPair = newKeyPair()

    @Test
    fun shouldEncryptAndDecrypt() {
        val message = KeysBase64Cipher.toBase64String(newSymmetricKey())
        val subscriber = TestSubscriber<String>()
        encrypt(message.toByteArray(), keyPair.public)
                .flatMap { decrypt(it, keyPair.private) }
                .map { String(it) }
                .subscribe(subscriber)
        subscriber.assertValue(message)
    }

    @Test
    fun shouldWorkTheOtherWayAround() {
        val message = KeysBase64Cipher.toBase64String(newSymmetricKey())
        val subscriber = TestSubscriber<String>()
        encrypt(message.toByteArray(), keyPair.private)
                .flatMap { decrypt(it, keyPair.public) }
                .map { String(it) }
                .subscribe(subscriber)
        subscriber.assertValue(message)
    }
}