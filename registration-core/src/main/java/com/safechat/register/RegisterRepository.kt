package com.safechat.register

interface RegisterRepository {
    fun isKeySaved(): Boolean

    fun saveNewKey(keyPair: KeyPairString)
}