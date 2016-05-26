package com.safechat.repository

import org.junit.Assert
import org.junit.Test

class RepositoryImplTest {

    @Test
    fun shouldReturnFalse() {
        Assert.assertFalse(RepositoryImpl().isKeySaved())
    }
}
