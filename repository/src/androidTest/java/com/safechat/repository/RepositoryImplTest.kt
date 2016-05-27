package com.safechat.repository

import android.support.test.InstrumentationRegistry.getTargetContext
import com.safechat.encryption.Encryptor.newKeyPair
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
        assertEquals(keyPair.private, repositoryImpl.getPrivateKey())
        assertEquals(keyPair.public, repositoryImpl.getPublicKey())
    }

    @After
    @Before
    fun clearKey() {
        repositoryImpl.sharedPreferences.edit()
                .remove("private_key")
                .remove("public_key")
                .apply()
    }
}
