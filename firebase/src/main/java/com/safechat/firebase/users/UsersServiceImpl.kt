package com.safechat.firebase.users

import com.google.firebase.database.*
import com.safechat.conversation.select.User
import com.safechat.conversation.select.UsersService
import rx.Observable
import rx.Subscriber
import java.util.*

class UsersServiceImpl : UsersService {
    override fun getUsers() = Observable.create<List<User>> { subscriber ->
        subscriber.onStart()
        FirebaseDatabase
                .getInstance()
                .reference
                .child("users")
                .addListenerForSingleValueEvent(Listener(subscriber))
    }

    class Listener(val subscriber: Subscriber<in List<User>>) : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            if (dataSnapshot == null) {
                finishCall(emptyList<User>())
            } else {
                val usersMap = dataSnapshot.getValue(object : GenericTypeIndicator<HashMap<String, UserRsa>>() {})
                finishCall(usersMap.map { User(it.value.rsa!!) })
            }
        }

        private fun finishCall(emptyList: List<User>) {
            subscriber.onNext(emptyList)
            subscriber.onCompleted()
        }

        override fun onCancelled(databaseError: DatabaseError) {
            subscriber.onError(databaseError.toException())
        }
    }

    class UserRsa() {
        var rsa: String? = null
    }
}