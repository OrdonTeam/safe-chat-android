package com.safechat.register

import rx.Observable

interface KeyGenerator {
    fun generateNewKeyPair(): Observable<KeyPairString>
}