package com.safechat.encryption

import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.generator.NewKeysGenerator
import com.safechat.register.KeyGenerator
import com.safechat.register.KeyPairString
import rx.Observable

class KeyGeneratorImpl : KeyGenerator {

    override fun generateNewKeyPair(): Observable<KeyPairString> {
        return NewKeysGenerator
                .generateNewKeyPair()
                .map {
                    KeyPairString(
                            KeysBase64Cipher.toBase64String(it.public),
                            KeysBase64Cipher.toBase64String(it.private))
                }
    }

}