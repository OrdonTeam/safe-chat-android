package com.safechat.firebase.conversation

import com.safechat.conversation.Message
import junit.framework.Assert.assertEquals
import org.junit.Test

class FirebaseMessageTest {

    val myUid = "my-uid"
    val otherUid = "other-uid"
    val timestamp = 1466490821381

    @Test
    fun shouldConvertMessageToFirebase() {
        val message = Message("text", false, false, timestamp)
        assertEquals(message, message.toFirebaseMessage(myUid, otherUid).toMessage(myUid, timestamp))
    }

    @Test
    fun shouldConvertMineMessageToFirebase() {
        val message = Message("text", true, false, timestamp)
        assertEquals(message, message.toFirebaseMessage(myUid, otherUid).toMessage(myUid, timestamp))
    }

    @Test
    fun shouldConvertReadMessageToFirebase() {
        val message = Message("text", false, true, timestamp)
        assertEquals(message, message.toFirebaseMessage(myUid, otherUid).toMessage(myUid, timestamp))
    }

    @Test
    fun shouldConvertMineReadMessageToFirebase() {
        val message = Message("text", true, true, timestamp)
        assertEquals(message, message.toFirebaseMessage(myUid, otherUid).toMessage(myUid, timestamp))
    }
}