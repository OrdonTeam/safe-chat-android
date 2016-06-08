package com.safechat.encryption

import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher

object Encryptor {

    fun encrypt(bytes: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(bytes)
    }

    fun decrypt(encrypted: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted)
    }

    fun newKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(1024)
        return keyPairGenerator.genKeyPair()
    }
}
