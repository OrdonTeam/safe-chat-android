package com.safechat.encryption

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ListExtensionsTest {

    @Test
    fun shouldSplitListIntoCorrectAmountOfSublists() {
        val list = "iAmYourList".toList()
        val partitionSize = 2

        val expectedSize = Math.round(list.size.toDouble() / partitionSize).toInt()
        assertEquals(expectedSize, list.split(partitionSize).size)
    }

    @Test
    fun shouldSlicesAfterJoinBeEqualToListFromBeginning() {
        val list = "iAmYourList".toList()
        val partitionSize = 2

        assertEquals(list, list.split(partitionSize).flatten())
    }

    @Test
    fun shouldEachSliceBeEqualSizeOrLessThanTheSizeOfPartition() {
        val list = "iAmYourList".toList()
        val partitionSize = 2

        assertTrue(list.split(partitionSize).all { it.size <= partitionSize })
    }
}