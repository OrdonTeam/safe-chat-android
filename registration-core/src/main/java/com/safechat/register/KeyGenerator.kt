package com.safechat.register

import rx.Observable
import java.security.KeyPair

interface KeyGenerator {
    fun generateNewKey(): Observable<KeyPair>
}