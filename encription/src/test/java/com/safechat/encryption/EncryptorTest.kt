package com.safechat.encryption

import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.KeyPair
import java.security.KeyPairGenerator

class EncryptorTest {

    val keyPair = keyPair()
    val message = "message".toByteArray()

    @Test
    fun shouldEncryptAndDecrypt() {
        val encrypted = Encryptor().encrypt(message, keyPair.public)
        val decrypted = Encryptor().decrypt(encrypted, keyPair.private)
        assertEquals(String(message), String(decrypted))
    }

    @Test
    fun shouldWorkTheOtherWayAround() {
        val message = "message".toByteArray()
        val encrypted = Encryptor().encrypt(message, keyPair.private)
        val decrypted = Encryptor().decrypt(encrypted, keyPair.public)
        assertEquals(String(message), String(decrypted))
    }

    @Test
    fun shouldAllowToEncryptMessageTwice() {
        val secondKeyPair = keyPair()
        val PARTITION_SIZE = 1024 / 8 - 11 //KEY_SIZE in bytes minus padding bytes
        val encrypted = Encryptor().encrypt(message, keyPair.private)

        val slices = encrypted.toList().split(PARTITION_SIZE)
        val encryptedSlices = slices.map { Encryptor().encrypt(it.toByteArray(), secondKeyPair.private) }
        val decryptedSlices = encryptedSlices.map { Encryptor().decrypt(it, secondKeyPair.public) }
        val joinedDecryptedSlices = decryptedSlices.reduce { first, second -> first + second }

        val decrypted = Encryptor().decrypt(joinedDecryptedSlices, keyPair.public)
        assertEquals(String(message), String(decrypted))
    }

    private fun keyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(1024)
        return keyPairGenerator.genKeyPair()
    }
}