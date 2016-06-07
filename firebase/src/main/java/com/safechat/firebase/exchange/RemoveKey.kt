package com.safechat.firebase.exchange

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import rx.Observable

fun removeKeyFromUserUid(otherUid: String) = Observable.create<Unit> { subscriber ->
    subscriber.onStart()

    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(uid)
            .child("pending_requests")
            .child(otherUid)
            .setValue(null)
            .addOnSuccessListener {
                subscriber.onNext(Unit)
                subscriber.onCompleted()
            }
            .addOnFailureListener {
                subscriber.onError(it)
            }
}
