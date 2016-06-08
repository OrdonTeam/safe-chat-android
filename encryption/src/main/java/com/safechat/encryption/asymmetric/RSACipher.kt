package com.safechat.encryption.asymmetric

import rx.Observable
import java.security.Key
import javax.crypto.Cipher

object RSACipher {

    fun encrypt(decrypted: ByteArray, key: Key): Observable<ByteArray> {
        return Observable.create {
            it.onStart()
            it.onNext(encryptImpl(decrypted, key))
            it.onCompleted()
        }
    }

    internal fun encryptImpl(bytes: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(bytes)
    }

    fun decrypt(encrypted: ByteArray, key: Key): Observable<ByteArray> {
        return Observable.create {
            it.onStart()
            it.onNext(decryptImpl(encrypted, key))
            it.onCompleted()
        }
    }

    internal fun decryptImpl(encrypted: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted)
    }
}