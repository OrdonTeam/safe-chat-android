package com.safechat.encryption

import com.safechat.register.KeyGenerator
import com.safechat.register.KeyPairString
import rx.Observable
import java.security.KeyPair
import java.security.KeyPairGenerator

class KeyGeneratorImpl : KeyGenerator {

    override fun generateNewKey(): Observable<KeyPairString> {
        return Observable.create {
            it.onStart()
            it.onNext(keyPair())
            it.onCompleted()
        }
    }

    private fun keyPair(): KeyPairString {
        val newKeyPair = Encryptor.newKeyPair()
        return KeyPairString(newKeyPair.public.toBase64String(),newKeyPair.private.toBase64String())
    }

}