package com.safechat.conversation

import rx.Observable

class UnsubscribeVerifier<T> {

    private var wasUnsubscribed = false
    val observable: Observable<T>

    constructor(observable: Observable<T>) {
        this.observable = observable.doOnUnsubscribe { wasUnsubscribed = true }
    }

    fun assertWasUnsubscribed() {
        assert(wasUnsubscribed, { "Unsubscribe expected but not invoked" })
    }

    companion object {

        fun <T> newUnsubscribeVerifier(): UnsubscribeVerifier<T> {
            return UnsubscribeVerifier(Observable.never<T>())
        }
    }
}