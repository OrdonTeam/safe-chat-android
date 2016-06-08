package com.safechat.encryption.generator

import rx.Observable
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object NewKeysGenerator {

    fun generateNewKeyPair(): Observable<KeyPair> {
        return Observable.create {
            it.onStart()
            it.onNext(newKeyPair())
            it.onCompleted()
        }
    }

    internal fun newKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(1024)
        return keyPairGenerator.genKeyPair()
    }

    fun generateNewSymmetricKey(): Observable<SecretKey> {
        return Observable.create {
            it.onStart()
            it.onNext(newSymmetricKey())
            it.onCompleted()
        }
    }

    internal fun newSymmetricKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey();
    }
}