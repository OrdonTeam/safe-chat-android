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
        assertEquals(keyPair.privateKey, repositoryImpl.getPrivateKey())
        assertEquals(keyPair.publicKey, repositoryImpl.getPublicKey())
    }

    private fun newKeyPair() = KeyPairString("publicKey", "privateKey")

    @After
    @Before
    fun clearKey() {
        repositoryImpl.sharedPreferences.edit()
                .remove("private_key")
                .remove("public_key")
                .apply()
    }
}
