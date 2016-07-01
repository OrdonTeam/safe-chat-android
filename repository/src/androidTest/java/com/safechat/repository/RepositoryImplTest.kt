package com.safechat.repository

import android.support.test.InstrumentationRegistry.getTargetContext
import com.safechat.message.Message
import com.safechat.register.KeyPairString
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RepositoryImplTest {

    val repositoryImpl = RepositoryImpl(getTargetContext())

    @Test
    fun shouldNotFindKeyBeforeKeySaved() {
        assertFalse(repositoryImpl.isKeySaved())
    }

    @Test
    fun shouldFindKeyAfterKeySaved() {
        repositoryImpl.saveNewKey(newKeyPair())
        assertTrue(repositoryImpl.isKeySaved())
    }

    @Test
    fun shouldReadSavedKey() {
        val keyPair = newKeyPair()
        repositoryImpl.saveNewKey(keyPair)
        assertEquals(keyPair.privateKey, repositoryImpl.getPrivateKeyString())
        assertEquals(keyPair.publicKey, repositoryImpl.getPublicKeyString())
    }

    @Test
    fun shouldSaveDecryptedSymmetricKey() {
        repositoryImpl.saveDecryptedSymmetricKey(otherPublicKey, "decrypted_symmetric_key")
        assertTrue(repositoryImpl.containsSymmetricKey(otherPublicKey))
    }

    @Test
    fun shouldSaveConversationMessage() {
        val message = Message("", false, false, 1L)
        repositoryImpl.saveConversationMessage(otherPublicKey, message)
        val messages = repositoryImpl.getConversationsMessages()
        assertEquals(message, messages.values.first())
    }

    @Test
    fun shouldOverrideMessageFromTheSamePublicKey(){
        val secondMessage = Message("", false, false, 2L)
        repositoryImpl.saveConversationMessage(otherPublicKey, Message("", false, false, 1L))
        repositoryImpl.saveConversationMessage(otherPublicKey, secondMessage)
        val messages = repositoryImpl.getConversationsMessages()
        assertEquals(secondMessage, messages.values.first())
    }


    private fun newKeyPair() = KeyPairString("publicKey", "privateKey")

    private val otherPublicKey = "other_public_key"

    @After
    @Before
    fun clearKey() {
        repositoryImpl.sharedPreferences.edit()
                .remove(RepositoryImpl.PRIVATE_KEY)
                .remove(RepositoryImpl.PUBLIC_KEY)
                .remove(RepositoryImpl.CONVERSATIONS)
                .remove(otherPublicKey)
                .apply()
    }
}
