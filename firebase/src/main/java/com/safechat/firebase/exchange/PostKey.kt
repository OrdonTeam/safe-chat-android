package com.safechat.firebase.exchange

import com.google.firebase.database.FirebaseDatabase
import rx.Observable

fun postSymmetricKeyToUserUid(myUid: String, otherUid: String, encryptedSymmetricKey: String) = Observable.create<Unit> { subscriber ->
    subscriber.onStart()
    FirebaseDatabase
            .getInstance()
            .reference
            .child("pending_requests")
            .child(otherUid)
            .child(myUid)
            .setValue(encryptedSymmetricKey)
            .addOnSuccessListener {
                subscriber.onNext(Unit)
                subscriber.onCompleted()
            }
            .addOnFailureListener {
                subscriber.onError(it)
            }
}
