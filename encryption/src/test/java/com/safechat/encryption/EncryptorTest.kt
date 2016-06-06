package com.safechat.encryption

import com.safechat.encryption.Encryptor.newKeyPair
import com.safechat.encryption.Encryptor.privateKeyFromBase64String
import com.safechat.encryption.Encryptor.publicKeyFromBase64String
import org.junit.Assert.assertEquals
import org.junit.Test

class EncryptorTest {

    val keyPair = newKeyPair()
    val message = "message".toByteArray()

    @Test
    fun shouldEncryptAndDecrypt() {
        val encrypted = Encryptor.encrypt(message, keyPair.public)
        val decrypted = Encryptor.decrypt(encrypted, keyPair.private)
        assertEquals(String(message), String(decrypted))
    }

    @Test
    fun shouldWorkTheOtherWayAround() {
        val message = "message".toByteArray()
        val encrypted = Encryptor.encrypt(message, keyPair.private)
        val decrypted = Encryptor.decrypt(encrypted, keyPair.public)
        assertEquals(String(message), String(decrypted))
    }

    @Test
    fun shouldAllowToEncryptMessageTwice() {
        val secondKeyPair = newKeyPair()
        val PARTITION_SIZE = 1024 / 8 - 11 //KEY_SIZE in bytes minus padding bytes
        val encrypted = Encryptor.encrypt(message, keyPair.private)

        val slices = encrypted.toList().split(PARTITION_SIZE)
        val encryptedSlices = slices.map { Encryptor.encrypt(it.toByteArray(), secondKeyPair.private) }
        val decryptedSlices = encryptedSlices.map { Encryptor.decrypt(it, secondKeyPair.public) }
        val joinedDecryptedSlices = decryptedSlices.reduce { first, second -> first + second }

        val decrypted = Encryptor.decrypt(joinedDecryptedSlices, keyPair.public)
        assertEquals(String(message), String(decrypted))
    }

    @Test
    fun shouldEncryptAndDecryptWithPersistedKey() {
        val encrypted = Encryptor.encrypt(message, keyPair.public)
        val decrypted = Encryptor.decrypt(encrypted, privateKeyFromBase64String(keyPair.private.toBase64String()))
        assertEquals(String(message), String(decrypted))
    }

    @Test
    fun shouldEncryptWithPersistedKeyAndDecrypt() {
        val encrypted = Encryptor.encrypt(message, publicKeyFromBase64String(keyPair.public.toBase64String()))
        val decrypted = Encryptor.decrypt(encrypted, keyPair.private)
        assertEquals(String(message), String(decrypted))
    }
}