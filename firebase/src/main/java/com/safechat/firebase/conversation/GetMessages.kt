package com.safechat.firebase.conversation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.safechat.conversation.Message
import rx.Observable
import java.util.*

fun getPreviousMessagesWithUid(otherUid: String): Observable<List<Message>> {
    return Observable.create { subscriber ->
        subscriber.onStart()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase
                .getInstance()
                .reference
                .child("conversations")
                .child(min(uid, otherUid))
                .child(max(uid, otherUid))
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(data: DataSnapshot?) {
                        if (data == null) {
                            subscriber.onNext(emptyList())
                            subscriber.onCompleted()
                        } else {
                            val value = data.getValue(object : GenericTypeIndicator<HashMap<String, GetMessage>>() {})
                            if (value == null) {
                                subscriber.onNext(emptyList())
                                subscriber.onCompleted()
                            } else {
                                subscriber.onNext(value.values.map { Message(it.message!!, it.sender == uid) })
                                subscriber.onCompleted()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        subscriber.onError(error.toException())
                    }
                })
    }
}

private class GetMessage {
    var message: String? = null
    var sender: String? = null
}