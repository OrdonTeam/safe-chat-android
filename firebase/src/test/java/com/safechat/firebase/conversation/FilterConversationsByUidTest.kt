package com.safechat.firebase.conversation

import com.safechat.firebase.conversations.filterConversationsByUid
import org.junit.Assert
import org.junit.Test

class FilterConversationsByUidTest {

    private val conversation = mapOf("b" to FirebaseMessage("t"))

    @Test
    fun shouldReturnMyConversationWhenIAmNotNested() {
        val myUid = "a"
        val given = mapOf(myUid to conversation)

        val actual = given.filterConversationsByUid(myUid)
        Assert.assertEquals(mapOf("b" to FirebaseMessage("t")), actual)
    }

    @Test
    fun shouldNotReturnNotMyConversationWhenIAmNotNested() {
        val myUid = "a"
        val otherUid = "c"
        val given = mapOf(otherUid to conversation)

        val actual = given.filterConversationsByUid(myUid)
        Assert.assertEquals(mapOf<String, FirebaseMessage>(), actual)
    }

    @Test
    fun shouldReturnMyConversationWhenIAmNested() {
        val myUid = "a"
        val given = mapOf("b" to mapOf(myUid to FirebaseMessage("t")))

        val actual = given.filterConversationsByUid(myUid)
        Assert.assertEquals(mapOf("b" to FirebaseMessage("t")), actual)
    }

    @Test
    fun shouldNotReturnMyConversationWhenIAmNested() {
        val myUid = "a"
        val otherUid = "c"
        val given = mapOf(otherUid to conversation)

        val actual = given.filterConversationsByUid(myUid)
        Assert.assertEquals(mapOf<String, FirebaseMessage>(), actual)
    }
}