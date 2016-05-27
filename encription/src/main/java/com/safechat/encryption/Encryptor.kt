package com.safechat.encryption

import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
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

    fun publicKeyFromBase64String(base64String: String): PublicKey {
        return KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(Base64.getDecoder().decode(base64String)))
    }

    fun privateKeyFromBase64String(base64String: String): PrivateKey {
        return KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64String)))
    }
}

fun Key.toBase64String() = String(Base64.getEncoder().encode(this.encoded))
