package com.safechat.encryption

import com.safechat.encryption.base.KeysBase64Cipher.toBase64String
import com.safechat.register.KeyGenerator
import com.safechat.register.KeyPairString
import rx.Observable

class KeyGeneratorImpl : KeyGenerator {

    override fun generateNewKeyPair(): Observable<KeyPairString> {
        return Observable.create {
            it.onStart()
            it.onNext(keyPair())
            it.onCompleted()
        }
    }

    private fun keyPair(): KeyPairString {
        val newKeyPair = Encryptor.newKeyPair()
        return KeyPairString(
                toBase64String(newKeyPair.public),
                toBase64String(newKeyPair.private))
    }

}