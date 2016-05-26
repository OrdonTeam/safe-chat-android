package com.safechat.register

import java.security.PublicKey

interface RegisterService {
    fun registerNewKey(publicKey: PublicKey)
}