package com.safechat.repository

import android.content.Context
import android.preference.PreferenceManager
import com.safechat.encryption.Encryptor
import com.safechat.encryption.Encryptor.privateKeyFromBase64String
import com.safechat.encryption.Encryptor.publicKeyFromBase64String
import com.safechat.encryption.toBase64String
import com.safechat.register.RegisterRepository
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey

class RepositoryImpl(context: Context) : RegisterRepository {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun isKeySaved(): Boolean {
        return sharedPreferences.contains(PUBLIC_KEY)
    }

    override fun saveNewKey(key: KeyPair) {
        sharedPreferences.edit()
                .putString(PUBLIC_KEY, key.public.toBase64String())
                .putString(PRIVATE_KEY, key.private.toBase64String())
                .apply()
    }

    fun getPublicKey(): PublicKey {
        return publicKeyFromBase64String(sharedPreferences.getString(PUBLIC_KEY, null))
    }

    fun getPrivateKey(): PrivateKey {
        return privateKeyFromBase64String(sharedPreferences.getString(PRIVATE_KEY, null))
    }

    companion object {
        val PUBLIC_KEY = "public_key"
        val PRIVATE_KEY = "private_key"
    }
}