package com.safechat.firebase.conversation

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.safechat.message.Message

internal data class FirebaseMessage(
        var message: String? = null,
        var sender: String? = null,
        var isRead: Boolean = false) {


    fun toMessage(myUid: String, timestamp: Long): com.safechat.message.Message {
        return com.safechat.message.Message(message!!, sender == myUid, isRead, timestamp)
    }
}

internal fun com.safechat.message.Message.toFirebaseMessage(myUid: String, otherUid: String): FirebaseMessage {
    val sender = if (isYours) myUid else otherUid
    return FirebaseMessage(this.text, sender, isRead)
}

internal fun DataSnapshot.toMessage(myUid: String, timestamp: Long): com.safechat.message.Message? {
    return getValue(object : GenericTypeIndicator<FirebaseMessage>() {})?.toMessage(myUid, timestamp)
}