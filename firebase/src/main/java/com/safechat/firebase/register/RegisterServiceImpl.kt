package com.safechat.firebase.register

import com.safechat.register.RegisterService
import rx.Observable

class RegisterServiceImpl : RegisterService {

    override fun registerNewKey(publicKey: String): Observable<Unit> {
        val email = publicKey + "@safechat.com"
        val password = publicKey + "safe"
        return createUser(email, password)
                .flatMap { loginUser(email, password) }
                .flatMap { putUser(it.user.uid, publicKey) }
    }
}
