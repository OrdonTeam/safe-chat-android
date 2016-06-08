package com.safechat.encryption.asymmetric

import com.safechat.encryption.util.asObservable
import rx.Observable
import java.security.Key
import javax.crypto.Cipher

object RSACipher {

    fun encrypt(decrypted: ByteArray, key: Key): Observable<ByteArray> {
        return { encryptImpl(decrypted, key) }.asObservable()
    }

    internal fun encryptImpl(bytes: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(bytes)
    }

    fun decrypt(encrypted: ByteArray, key: Key): Observable<ByteArray> {
        return { decryptImpl(encrypted, key) }.asObservable()
    }

    internal fun decryptImpl(encrypted: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted)
    }
}