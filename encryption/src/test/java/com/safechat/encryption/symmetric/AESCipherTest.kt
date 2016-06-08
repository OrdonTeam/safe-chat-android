package com.safechat.encryption.symmetric

import com.safechat.encryption.generator.newSymmetricKey
import com.safechat.encryption.symmetric.AESCipher.decrypt
import com.safechat.encryption.symmetric.AESCipher.encrypt
import org.junit.Test
import rx.observers.TestSubscriber

class AESCipherTest {

    val key = newSymmetricKey()

    @Test
    fun shouldEncryptAndDecrypt() {
        val message = "message"
        val subscriber = TestSubscriber<String>()
        encrypt(message.toByteArray(), key)
                .flatMap { decrypt(it, key) }
                .map { String(it) }
                .subscribe(subscriber)
        subscriber.assertValue(message)
    }
}