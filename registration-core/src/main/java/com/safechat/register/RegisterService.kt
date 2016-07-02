package com.safechat.register

import rx.Observable

interface RegisterService {
    fun registerNewKey(publicKey: String): Observable<Unit>
}