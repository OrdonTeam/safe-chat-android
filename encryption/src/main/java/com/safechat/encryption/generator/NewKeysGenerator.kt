package com.safechat.encryption.generator

import rx.Observable
import java.security.KeyPair
import java.security.KeyPairGenerator

object NewKeysGenerator {

    fun generateNewKeyPair(): Observable<KeyPair> {
        return Observable.create {
            it.onStart()
            it.onNext(newKeyPair())
            it.onCompleted()
        }
    }

    private fun newKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(1024)
        return keyPairGenerator.genKeyPair()
    }
}