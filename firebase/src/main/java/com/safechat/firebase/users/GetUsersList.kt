package com.safechat.firebase.users

import com.google.firebase.database.*
import rx.Observable
import java.util.*


fun getUsersList() = Observable.create<List<UserUidAndRsa>> { subscriber ->
    subscriber.onStart()
    FirebaseDatabase
            .getInstance()
            .reference
            .child("users")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    if (dataSnapshot == null) {
                        subscriber.onNext(emptyList())
                        subscriber.onCompleted()
                    } else {
                        val usersMap = dataSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, UserRsa>>() {})
                        subscriber.onNext(usersMap.map { UserUidAndRsa(it.key, it.value.rsa!!) })
                        subscriber.onCompleted()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    subscriber.onError(databaseError.toException())
                }
            })
}

data class UserUidAndRsa(val uid: String, val rsa: String)

private class UserRsa() {
    var rsa: String? = null
}
