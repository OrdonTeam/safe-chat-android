package com.safechat.register

import java.security.KeyPair

interface RegisterRepository {
    fun isKeySaved(): Boolean

    fun saveNewKey(key: KeyPair)
}