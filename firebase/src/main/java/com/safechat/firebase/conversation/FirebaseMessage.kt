package com.safechat.firebase.conversation

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.safechat.conversation.Message

internal data class FirebaseMessage(
        var message: String? = null,
        var sender: String? = null,
        var isRead: Boolean = false) {


    fun toMessage(uid: String, timestamp: Long): Message {
        return Message(message!!, sender == uid, isRead, timestamp)
    }
}

internal fun Message.toFirebaseMessage(uid: String, otherUid: String): FirebaseMessage {
    val sender = if (isYours) uid else otherUid
    return FirebaseMessage(this.text, sender, isRead)
}

internal fun DataSnapshot.toMessage(uid: String, timestamp: Long): Message? {
    return getValue(object : GenericTypeIndicator<FirebaseMessage>() {})?.toMessage(uid, timestamp)
}