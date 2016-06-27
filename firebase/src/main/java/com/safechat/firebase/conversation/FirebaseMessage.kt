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

internal fun Message.toFirebaseMessage(uid: String): FirebaseMessage {
    return FirebaseMessage(this.text, uid, false)
}

internal fun DataSnapshot.toMessage(uid: String, timestamp: Long): Message? {
    return getValue(object : GenericTypeIndicator<FirebaseMessage>() {})?.toMessage(uid, timestamp)
}