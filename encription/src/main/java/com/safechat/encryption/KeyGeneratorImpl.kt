package com.safechat.encryption

import com.safechat.register.KeyGenerator
import rx.Observable
import java.security.KeyPair
import java.security.KeyPairGenerator

class KeyGeneratorImpl : KeyGenerator {

    override fun generateNewKey(): Observable<KeyPair> {
        return Observable.create {
            it.onStart()
            it.onNext(Encryptor.newKeyPair())
            it.onCompleted()
        }
    }

}