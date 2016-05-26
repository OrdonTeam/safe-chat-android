package com.safechat.repository

import com.safechat.register.RegisterRepository
import java.security.KeyPair

class RepositoryImpl : RegisterRepository {

    override fun isKeySaved(): Boolean {
        return false
    }

    override fun saveNewKey(key: KeyPair) {
    }
}