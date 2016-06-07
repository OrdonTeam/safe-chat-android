package com.safechat.firebase.conversation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.safechat.conversation.Message
import rx.Observable

fun postMessageToUid(otherUid: String, message: Message): Observable<Unit> {
    return Observable.create { subscriber ->
        subscriber.onStart()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase
                .getInstance()
                .reference
                .child("conversations")
                .child(min(uid, otherUid))
                .child(max(uid, otherUid))
                .child(System.currentTimeMillis().toString())
                .setValue(FirebaseMessage(message.text, uid))
                .addOnSuccessListener {
                    subscriber.onNext(Unit)
                    subscriber.onCompleted()
                }
                .addOnFailureListener {
                    subscriber.onError(it)
                }
    }
}

fun min(uid: String, otherUid: String): String {
    return if (uid.compareTo(otherUid) > 0) uid else otherUid
}

fun max(uid: String, otherUid: String): String {
    return if (uid.compareTo(otherUid) > 0) otherUid else uid
}

private data class FirebaseMessage(val message: String, val sender: String)
