package com.safechat.firebase.conversation

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.safechat.conversation.Message

internal data class FirebaseMessage(
        var message: String? = null,
        var sender: String? = null,
        var isRead: Boolean = false) {


    fun toMessage(myUid: String, timestamp: Long): Message {
        return Message(message!!, sender == myUid, isRead, timestamp)
    }
}

internal fun Message.toFirebaseMessage(myUid: String, otherUid: String): FirebaseMessage {
    val sender = if (isYours) myUid else otherUid
    return FirebaseMessage(this.text, sender, isRead)
}

internal fun DataSnapshot.toMessage(myUid: String, timestamp: Long): Message? {
    return getValue(object : GenericTypeIndicator<FirebaseMessage>() {})?.toMessage(myUid, timestamp)
}