package com.safechat.firebase

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import rx.Observable

fun loginUser(email: String, password: String) = Observable.create<AuthResult> { subscriber ->
    subscriber.onStart()
    FirebaseAuth
            .getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                subscriber.onNext(it)
                subscriber.onCompleted()
            }
            .addOnFailureListener {
                subscriber.onError(it)
            }
}