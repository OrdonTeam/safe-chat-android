package com.safechat.register

import rx.Observable

interface KeyGenerator {
    fun generateNewKey(): Observable<KeyPairString>
}