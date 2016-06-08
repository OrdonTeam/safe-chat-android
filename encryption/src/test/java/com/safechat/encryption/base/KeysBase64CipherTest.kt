package com.safechat.encryption.base

import com.safechat.encryption.base.KeysBase64Cipher.decodePrivateKey
import com.safechat.encryption.base.KeysBase64Cipher.decodePublicKey
import com.safechat.encryption.base.KeysBase64Cipher.decodeSymmetricKey
import com.safechat.encryption.base.KeysBase64Cipher.toBase64String
import com.safechat.encryption.generator.newKeyPair
import com.safechat.encryption.generator.newSymmetricKey
import org.junit.Assert.assertEquals
import org.junit.Test

class KeysBase64CipherTest {

    val keyPair = newKeyPair()
    val symmetricKey = newSymmetricKey()

    @Test
    fun shouldEncryptAndDecryptKeysToBase64() {
        assertEquals(symmetricKey, decodeSymmetricKey(toBase64String(symmetricKey)))
        assertEquals(keyPair.public, decodePublicKey(toBase64String(keyPair.public)))
        assertEquals(keyPair.private, decodePrivateKey(toBase64String(keyPair.private)))
    }
}