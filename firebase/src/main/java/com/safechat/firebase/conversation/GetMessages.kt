package com.safechat.firebase.conversation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
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
                        if (data != null) {
                            val message = data.toMessage(uid, data.key!!.toLong())
                            if (message != null) {
                                subscriber.onNext(message)
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
