package com.safechat.repository

import android.support.test.InstrumentationRegistry.getTargetContext
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
        repositoryImpl.saveDecryptedSymmetricKey("other_public_key", "decrypted_symmetric_key")
        assertTrue(repositoryImpl.containsSymmetricKey("other_public_key"))
    }

    private fun newKeyPair() = KeyPairString("publicKey", "privateKey")

    @After
    @Before
    fun clearKey() {
        repositoryImpl.sharedPreferences.edit()
                .remove("private_key")
                .remove("public_key")
                .remove("other_public_key")
                .apply()
    }
}
