package com.safechat.register

import rx.Observable
import java.security.PublicKey

interface RegisterService {
    fun registerNewKey(publicKey: String): Observable<Unit>
}