package com.safechat.firebase

import com.google.firebase.database.FirebaseDatabase
import com.safechat.encryption.toBase64String
import com.safechat.register.RegisterService
import rx.Observable
import java.security.PublicKey

class RegisterServiceImpl : RegisterService {

    override fun registerNewKey(publicKey: PublicKey): Observable<Unit> {
        return Observable.create { subscriber ->
            subscriber.onStart()
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("message");
            myRef.setValue(publicKey.toBase64String()).addOnSuccessListener {
                subscriber.onNext(Unit)
                subscriber.onCompleted()
            };
        }
    }
}