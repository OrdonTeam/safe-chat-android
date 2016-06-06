package com.safechat.firebase.exchange

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import rx.Observable

fun postSymmetricKeyToUserUid(myPublicKey: String, otherUid: String, encryptedSymmetricKey: String) = Observable.create<Unit> { subscriber ->
    subscriber.onStart()

    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(otherUid)
            .child("pending_requests")
            .child(uid)
            .setValue(PendingRequest(myPublicKey, encryptedSymmetricKey, "TODO"))
            .addOnSuccessListener {
                subscriber.onNext(Unit)
                subscriber.onCompleted()
            }
            .addOnFailureListener {
                subscriber.onError(it)
            }
}

private data class PendingRequest(val rsa: String, val encryptedSymmetricKey: String, val conversationId: String)