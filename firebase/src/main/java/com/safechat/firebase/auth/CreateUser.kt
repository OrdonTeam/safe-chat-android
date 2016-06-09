package com.safechat.firebase.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import rx.Observable

fun createUser(email: String, password: String) = Observable.create<AuthResult> { subscriber ->
    subscriber.onStart()
    FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                subscriber.onNext(it)
                subscriber.onCompleted()
            }
            .addOnFailureListener {
                subscriber.onError(it)
            }
}