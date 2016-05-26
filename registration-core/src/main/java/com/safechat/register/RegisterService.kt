package com.safechat.register

import rx.Observable
import java.security.PublicKey

interface RegisterService {
    fun registerNewKey(publicKey: PublicKey): Observable<Unit>
}