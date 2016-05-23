package com.safechat.encryption

import org.junit.Assert.assertEquals
import org.junit.Test
import java.security.KeyPair
import java.security.KeyPairGenerator

class EncryptorTest {

    val keyPair = keyPair()

    @Test
    fun shouldEncryptAndDecrypt() {
        val message = "message".toByteArray()
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

    private fun keyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(1024)
        return keyPairGenerator.genKeyPair()
    }
}