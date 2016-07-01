package com.safechat.firebase.conversation

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.safechat.message.Message
import rx.Observable
import rx.schedulers.Schedulers

fun getPreviousMessagesWithUid(otherUid: String): Observable<com.safechat.message.Message> {
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

                    override fun onChildChanged(data: DataSnapshot?, p1: String?) {
                        if (data != null) {
                            onData(data)
                        }
                    }

                    override fun onChildAdded(data: DataSnapshot?, p1: String?) {
                        if (data != null) {
                            onData(data)
                        }
                    }

                    private fun onData(data: DataSnapshot) {
                        val message = data.toMessage(uid, data.key!!.toLong())
                        if (message != null) {
                            subscriber.onNext(message)
                            if (!message.isYours && !message.isRead) {
                                updateMessage(message)
                            }
                        }
                    }

                    private fun updateMessage(message: com.safechat.message.Message) {
                        postMessageToUid(otherUid, message.copy(isRead = true))
                                .subscribeOn(Schedulers.trampoline())
                                .subscribe({}, { subscriber.onError(it) })
                    }

                    override fun onChildRemoved(p0: DataSnapshot?) {
                    }

                    override fun onCancelled(error: DatabaseError) {
                        subscriber.onError(error.toException())
                    }
                })
    }
}
