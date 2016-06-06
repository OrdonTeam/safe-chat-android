package com.safechat.repository

import android.content.Context
import android.preference.PreferenceManager
import com.safechat.register.KeyPairString
import com.safechat.register.RegisterRepository

class RepositoryImpl(context: Context) : RegisterRepository {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun isKeySaved(): Boolean {
        return sharedPreferences.contains(PUBLIC_KEY) && sharedPreferences.contains(PRIVATE_KEY)
    }

    override fun saveNewKey(keyPair: KeyPairString) {
        sharedPreferences.edit()
                .putString(PUBLIC_KEY, keyPair.publicKey)
                .putString(PRIVATE_KEY, keyPair.privateKey)
                .apply()
    }

    fun getPublicKey(): String {
        return sharedPreferences.getString(PUBLIC_KEY, null)
    }

    fun getPrivateKey(): String {
        return sharedPreferences.getString(PRIVATE_KEY, null)
    }

    companion object {
        val PUBLIC_KEY = "public_key"
        val PRIVATE_KEY = "private_key"
    }
}