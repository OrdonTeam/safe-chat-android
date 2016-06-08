package com.safechat.encryption.symmetric

import com.safechat.encryption.util.asObservable
import rx.Observable
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

object AESCipher {

    fun encrypt(decrypted: ByteArray, key: Key): Observable<ByteArray> {
        return { encryptImpl(decrypted, key) }.asObservable()
    }

    internal fun encryptImpl(decrypted: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, key, IvParameterSpec(key.encoded))
        return cipher.doFinal(decrypted)
    }

    fun decrypt(encrypted: ByteArray, key: Key): Observable<ByteArray> {
        return { decryptImpl(encrypted, key) }.asObservable()
    }

    internal fun decryptImpl(encrypted: ByteArray, key: Key): ByteArray {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(key.encoded))
        return cipher.doFinal(encrypted)
    }
}