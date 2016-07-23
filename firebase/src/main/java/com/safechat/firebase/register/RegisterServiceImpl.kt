package com.safechat.firebase.register

import com.safechat.firebase.auth.createUser
import com.safechat.firebase.auth.loginUser
import com.safechat.firebase.users.putUser
import com.safechat.register.RegisterService
import rx.Observable
import java.util.*

class RegisterServiceImpl : RegisterService {

    override fun registerNewKey(publicKey: String): Observable<Unit> {
        val email = publicKey + "@safechat.com"
        val password = UUID.randomUUID().toString() + UUID.randomUUID().toString()
        return createUser(email, password)
                .flatMap { loginUser(email, password) }
                .flatMap { putUser(it.user.uid, publicKey) }
    }
}
