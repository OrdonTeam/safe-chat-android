package com.safechat.encryption.util

import rx.Observable

fun <T> (() -> T).asObservable(): Observable<T> {
    return Observable.create<T> {
        try {
            it.onStart()
            it.onNext(this())
            it.onCompleted()
        } catch(throwable: Throwable) {
            it.onError(throwable)
        }
    }
}