package com.safechat.firebase.exchange

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import rx.Observable

fun retrieveKeyFromUserUid(otherUid: String) = Observable.create<String?> { subscriber ->
    subscriber.onStart()

    val uid = FirebaseAuth.getInstance().currentUser!!.uid
    FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .child(uid)
            .child("pending_requests")
            .child(otherUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot == null) {
                        subscriber.onNext(null)
                        subscriber.onCompleted()
                    } else {
                        val pendingRequest = dataSnapshot.getValue(object : GenericTypeIndicator<GetPendingRequest>() {})
                        subscriber.onNext(pendingRequest?.encryptedSymmetricKey)
                        subscriber.onCompleted()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    subscriber.onError(databaseError.toException())
                }
            })
}

private class GetPendingRequest {
    val encryptedSymmetricKey: String? = null
}