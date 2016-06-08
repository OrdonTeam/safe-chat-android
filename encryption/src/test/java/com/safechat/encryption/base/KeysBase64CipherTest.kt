package com.safechat.encryption.base

import com.safechat.encryption.Encryptor
import com.safechat.encryption.base.KeysBase64Cipher.decodePrivateKey
import com.safechat.encryption.base.KeysBase64Cipher.decodePublicKey
import com.safechat.encryption.base.KeysBase64Cipher.toBase64String
import org.junit.Assert.assertEquals
import org.junit.Test

class KeysBase64CipherTest {

    val keyPair = Encryptor.newKeyPair()

    @Test
    fun shouldEncryptAndDecryptKeysToBase64() {
        assertEquals(keyPair.public, decodePublicKey(toBase64String(keyPair.public)))
        assertEquals(keyPair.private, decodePrivateKey(toBase64String(keyPair.private)))
    }
}