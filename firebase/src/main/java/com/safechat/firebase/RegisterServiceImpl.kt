package com.safechat.firebase

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.safechat.encryption.toBase64String
import com.safechat.register.RegisterService
import rx.Observable
import rx.Subscriber
import java.security.PublicKey

class RegisterServiceImpl : RegisterService {

    override fun registerNewKey(publicKey: PublicKey): Observable<Unit> {
        return Observable.create { subscriber ->
            OnSubscribe(subscriber, publicKey.toBase64String()).doOnSubscribe();
        }
    }

    private class OnSubscribe(val subscriber: Subscriber<in Unit>, val publicKeyString: String) {

        val email = publicKeyString + "@safechat.com"
        val password = publicKeyString + "safe"

        fun doOnSubscribe() {
            subscriber.onStart()
            register()
        }

        private fun register() {
            FirebaseAuth
                    .getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(logIn)
                    .addOnFailureListener(onError)
        }

        private val logIn: (AuthResult) -> Unit = {
            FirebaseAuth
                    .getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(putUserToDataBase)
                    .addOnFailureListener(onError)
        }

        private val putUserToDataBase: (AuthResult) -> Unit = {
            FirebaseDatabase
                    .getInstance()
                    .reference
                    .child("users")
                    .child(it.user.uid)
                    .setValue(EmptyUser(publicKeyString))
                    .addOnSuccessListener(onSuccess)
                    .addOnFailureListener(onError)
        }

        private val onSuccess: (Void?) -> Unit = {
            subscriber.onNext(Unit)
            subscriber.onCompleted()
        }

        private val onError: (Exception) -> Unit = {
            Log.e("Log error", it.toString(), it)
            subscriber.onError(it)
        }
    }

    private data class EmptyUser(val rsa: String)
}
