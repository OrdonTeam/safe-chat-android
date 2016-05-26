package com.safechat.repository

import android.support.test.InstrumentationRegistry.getTargetContext
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.security.KeyPair

class RepositoryImplTest {

    val repositoryImpl = RepositoryImpl(getTargetContext())

    @Test
    fun shouldNotFindKeyBeforeKeySaved() {
        assertFalse(repositoryImpl.isKeySaved())
    }

    @Test
    fun shouldFindKeyAfterKeySaved() {
        repositoryImpl.saveNewKey(KeyPair(null, null))
        assertTrue(repositoryImpl.isKeySaved())
    }

    @After
    @Before
    fun clearKey() {
        repositoryImpl.sharedPreferences.edit().remove("key_pair").apply()
    }
}
