package com.safechat.firebase.exchange

import com.google.firebase.database.FirebaseDatabase
import rx.Observable

fun removeKeyFromUserUid(myUid: String, otherUid: String) = Observable.create<Unit> { subscriber ->
    subscriber.onStart()
    FirebaseDatabase
            .getInstance()
            .reference
            .child("pending_requests")
            .child(myUid)
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
