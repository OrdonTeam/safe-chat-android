package com.safechat.encryption.asymmetric

import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.generator.newKeyPair
import com.safechat.encryption.generator.newSymmetricKey
import org.junit.Test
import rx.observers.TestSubscriber

class LongArraysRSACipherTest {

    val first = newKeyPair()
    val second = newKeyPair()

    @Test
    fun shouldEncryptAndDecrypt() {
        val message = KeysBase64Cipher.toBase64String(newSymmetricKey())
        val subscriber = TestSubscriber<String>()
        RSACipher.encrypt(message.toByteArray(), first.public)
                .flatMap { LongArraysRSACipher.encrypt(it, second.private) }
                .flatMap { LongArraysRSACipher.decrypt(it, second.public) }
                .flatMap { RSACipher.decrypt(it, first.private) }
                .map { String(it) }
                .subscribe(subscriber)
        subscriber.assertValue(message)
    }
}