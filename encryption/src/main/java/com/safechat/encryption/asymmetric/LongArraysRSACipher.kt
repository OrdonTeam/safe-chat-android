package com.safechat.encryption.asymmetric

import rx.Observable
import java.security.Key
import javax.crypto.Cipher

object LongArraysRSACipher {

    fun encrypt(decrypted: ByteArray, key: Key): Observable<List<ByteArray>> {
        return Observable.create {
            it.onStart()
            it.onNext(encryptImpl(decrypted, key))
            it.onCompleted()
        }
    }

    internal fun encryptImpl(bytes: ByteArray, key: Key): List<ByteArray> {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return bytes.toList().split(117).map { cipher.doFinal(it.toByteArray()) }
    }

    fun decrypt(encrypted: List<ByteArray>, key: Key): Observable<ByteArray> {
        return Observable.create {
            it.onStart()
            it.onNext(decryptImpl(encrypted, key))
            it.onCompleted()
        }
    }

    internal fun decryptImpl(encrypted: List<ByteArray>, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return encrypted.map { cipher.doFinal(it) }.reduce { first, second -> first + second }
    }
}