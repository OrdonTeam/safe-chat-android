package com.safechat.encryption.generator

import com.safechat.encryption.util.asObservable
import rx.Observable
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object NewKeysGenerator {

    fun generateNewKeyPair(): Observable<KeyPair> {
        return { newKeyPair() }.asObservable()
    }

    internal fun newKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(1024)
        return keyPairGenerator.genKeyPair()
    }

    fun generateNewSymmetricKey(): Observable<SecretKey> {
        return { newSymmetricKey() }.asObservable()
    }

    internal fun newSymmetricKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey();
    }
}