package com.safechat.firebase

import com.google.firebase.database.FirebaseDatabase
import com.safechat.encryption.toBase64String
import com.safechat.register.RegisterService
import rx.Observable
import rx.Subscriber
import java.security.PublicKey

class RegisterServiceImpl : RegisterService {

    override fun registerNewKey(publicKey: PublicKey): Observable<Unit> {
        return Observable.create { subscriber ->
            OnSubscribe(subscriber, publicKey).doOnSubscribe();
        }
    }

    private class OnSubscribe(val subscriber: Subscriber<in Unit>, val publicKey: PublicKey) {
        fun doOnSubscribe() {
            subscriber.onStart()
            FirebaseDatabase
                    .getInstance()
                    .reference
                    .child("users")
                    .child(publicKey.toBase64String())
                    .setValue(true)
                    .addOnSuccessListener(onSuccess)
                    .addOnFailureListener(onError)
        }

        val onSuccess: (Void?) -> Unit = {
            subscriber.onNext(Unit)
            subscriber.onCompleted()
        }
        val onError: (Exception) -> Unit = {
            subscriber.onError(it)
            subscriber.onCompleted()
        }
    }
}