package com.safechat.repository

import android.content.Context
import android.preference.PreferenceManager
import com.safechat.register.RegisterRepository
import java.security.KeyPair

class RepositoryImpl(context: Context) : RegisterRepository {

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun isKeySaved(): Boolean {
        return sharedPreferences.contains(KEY_PAIR)
    }

    override fun saveNewKey(key: KeyPair) {
        sharedPreferences.edit().putString(KEY_PAIR, KEY_PAIR).apply()
    }

    companion object {
        val KEY_PAIR = "key_pair"
    }
}