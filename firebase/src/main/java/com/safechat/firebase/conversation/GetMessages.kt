package com.safechat.firebase.conversation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.safechat.conversation.Message
import rx.Observable

fun getPreviousMessagesWithUid(otherUid: String): Observable<Message> {
    return Observable.create { subscriber ->
        subscriber.onStart()
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        FirebaseDatabase
                .getInstance()
                .reference
                .child("conversations")
                .child(min(uid, otherUid))
                .child(max(uid, otherUid))
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                    }

                    override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                    }

                    override fun onChildAdded(data: DataSnapshot?, p1: String?) {
                        if (data == null) {
                            subscriber.onError(RuntimeException("Empty data set"))
                        } else {
                            val value = data.getValue(object : GenericTypeIndicator<GetMessage>() {})
                            if (value == null) {
                                subscriber.onError(RuntimeException("Empty data set"))
                            } else {
                                subscriber.onNext(Message(value.message!!, value.sender == uid, false, data.key!!.toLong()))
                            }
                        }
                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {
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