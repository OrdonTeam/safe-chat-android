package com.safechat.encryption.generator

import org.junit.Assert.assertArrayEquals
import org.junit.Test
import rx.observers.TestSubscriber
import java.security.Key
import java.security.KeyPair
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class NewKeysGeneratorTest {

    @Test
    fun generatedKeyShouldBeReallyAsymmetric() {
        val subscriber = TestSubscriber<KeyPair>()
        NewKeysGenerator.generateNewKeyPair().subscribe (subscriber)
        val keyPair = subscriber.onNextEvents.first()
        verifySuccessfulAsymmetricEncryption(keyPair.public, keyPair.private)
    }

    @Test
    fun generatedKeyShouldBeReallySymmetric() {
        val subscriber = TestSubscriber<SecretKey>()
        NewKeysGenerator.generateNewSymmetricKey().subscribe (subscriber)
        val key = subscriber.onNextEvents.first()
        verifySuccessfulSymmetricEncryption(key)
    }

    private fun verifySuccessfulAsymmetricEncryption(one: Key, other: Key) {
        val bytes = "message".toByteArray()
        assertArrayEquals(bytes, encryptAndDecrypt(bytes, one, other))
    }

    private fun encryptAndDecrypt(bytes: ByteArray, one: Key, other: Key): ByteArray {
        val encryptor = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        encryptor.init(Cipher.ENCRYPT_MODE, one)
        val encrypted = encryptor.doFinal(bytes)
        val decryptor = Cipher.getInstance("RSA/ECB/PKCS1Padding")
        decryptor.init(Cipher.DECRYPT_MODE, other)
        return decryptor.doFinal(encrypted)
    }

    private fun verifySuccessfulSymmetricEncryption(key: SecretKey) {
        val bytes = "message".toByteArray()
        assertArrayEquals(bytes, encryptAndDecrypt(bytes, key))
    }

    private fun encryptAndDecrypt(bytes: ByteArray, key: SecretKey): ByteArray {
        val encryptor = Cipher.getInstance("AES/CBC/PKCS5Padding")
        encryptor.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(key.encoded))
        val encrypted = encryptor.doFinal(bytes)
        val decryptor = Cipher.getInstance("AES/CBC/PKCS5Padding")
        decryptor.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(key.encoded))
        return decryptor.doFinal(encrypted)
    }
}