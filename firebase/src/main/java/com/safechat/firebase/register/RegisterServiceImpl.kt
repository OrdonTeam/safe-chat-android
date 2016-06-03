package com.safechat.firebase.register

import com.safechat.encryption.toBase64String
import com.safechat.register.RegisterService
import rx.Observable
import java.security.PublicKey

class RegisterServiceImpl : RegisterService {

    override fun registerNewKey(publicKey: PublicKey): Observable<Unit> {
        val publicKeyString = publicKey.toBase64String()
        val email = publicKeyString + "@safechat.com"
        val password = publicKeyString + "safe"
        return createUser(email, password)
                .flatMap { loginUser(email, password) }
                .flatMap { putUser(it.user.uid, publicKeyString) }
    }
}
