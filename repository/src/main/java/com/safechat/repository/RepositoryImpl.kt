package com.safechat.repository

import android.content.Context
import android.preference.PreferenceManager
import com.elpassion.android.commons.sharedpreferences.createSharedPrefs
import com.safechat.conversation.ConversationRepository
import com.safechat.conversation.Message
import com.safechat.conversation.symmetrickey.ExchangeSymmetricKeyRepository
import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyRepository
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyRepository
import com.safechat.register.KeyPairString
import com.safechat.register.RegisterRepository
import com.safechat.user.profile.UserProfileRepository

class RepositoryImpl(context: Context) : RegisterRepository,
        ExchangeSymmetricKeyRepository,
        PostSymmetricKeyRepository,
        RetrieveSymmetricKeyRepository,
        ConversationRepository,
        UserProfileRepository {

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

    override fun containsSymmetricKey(otherPublicKey: String): Boolean {
        return sharedPreferences.contains(otherPublicKey)
    }

    override fun saveDecryptedSymmetricKey(otherPublicKey: String, decryptedSymmetricKey: String) {
        sharedPreferences.edit().putString(otherPublicKey, decryptedSymmetricKey).apply()
    }

    override fun getPublicKeyString(): String {
        return sharedPreferences.getString(PUBLIC_KEY, null)
    }

    override fun getPrivateKeyString(): String {
        return sharedPreferences.getString(PRIVATE_KEY, null)
    }

    override fun saveConversationMessage(otherPublicKey: String, message: Message) {
        val typedSharedPrefs = createSharedPrefs<Map<String, Message>>({ sharedPreferences })
        val conversations = typedSharedPrefs.read(CONVERSATIONS) ?: emptyMap()
        typedSharedPrefs.write(CONVERSATIONS, conversations + (otherPublicKey to message))
    }

    override fun getDecryptedSymmetricKey(otherPublicKey: String): String {
        return sharedPreferences.getString(otherPublicKey, null)
    }

    companion object {
        val PUBLIC_KEY = "public_key"
        val PRIVATE_KEY = "private_key"
        val CONVERSATIONS = "conversations"
    }
}