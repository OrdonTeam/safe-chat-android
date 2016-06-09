package com.safechat.firebase.users

import com.google.firebase.database.FirebaseDatabase
import rx.Observable

fun putUser(uid: String, publicKeyString: String) = Observable.create<Unit> { subscriber ->
    subscriber.onStart()
    FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(uid)
            .setValue(publicKeyString)
            .addOnSuccessListener{
                subscriber.onNext(Unit)
                subscriber.onCompleted()
            }
            .addOnFailureListener {
                subscriber.onError(it)
            }
}
