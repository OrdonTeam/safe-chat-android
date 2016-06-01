package com.safechat.firebase

import com.google.firebase.database.*
import com.safechat.key.list.KeyListService
import rx.Observable
import rx.Subscriber
import java.util.*

class KeyListServiceImpl : KeyListService {

    override fun keys(): Observable<List<String>> {
        return Observable.create { subscriber ->
            OnSubscribe(subscriber).doOnSubscribe()
        }
    }

    private class OnSubscribe(val subscriber: Subscriber<in List<String>>) {
        fun doOnSubscribe() {
            subscriber.onStart()
            FirebaseDatabase
                    .getInstance()
                    .reference
                    .child("users")
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(p0: DataSnapshot?) {
                            if (p0 != null) {
                                val type = object : GenericTypeIndicator<HashMap<String,Boolean>>() {}
                                onSuccess(p0.getValue(type).keys.toList())
                            }else{
                                onSuccess(emptyList())
                            }
                        }

                        override fun onCancelled(p0: DatabaseError?) {
                            onError(RuntimeException(p0?.message))
                        }
                    })
        }

        val onSuccess: (List<String>) -> Unit = {
            subscriber.onNext(it)
            subscriber.onCompleted()
        }
        val onError: (Exception) -> Unit = {
            subscriber.onError(it)
            subscriber.onCompleted()
        }
    }
}