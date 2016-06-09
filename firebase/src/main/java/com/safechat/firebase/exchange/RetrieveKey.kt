package com.safechat.firebase.exchange

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import rx.Observable

fun retrieveKeyFromUserUid(myUid: String, otherUid: String) = Observable.create<String?> { subscriber ->
    subscriber.onStart()
    FirebaseDatabase
            .getInstance()
            .reference
            .child("pending_requests")
            .child(myUid)
            .child(otherUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot == null) {
                        subscriber.onNext(null)
                        subscriber.onCompleted()
                    } else {
                        subscriber.onNext(dataSnapshot.getValue(String::class.java))
                        subscriber.onCompleted()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    subscriber.onError(databaseError.toException())
                }
            })
}
