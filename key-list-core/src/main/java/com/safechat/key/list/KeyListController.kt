package com.safechat.key.list

import rx.Subscription

class KeyListController(private val view: KeyListView,
                        private val service: KeyListService) {

    private var subscription: Subscription? = null

    fun start() {
        subscription = service.keys()
                .doOnSubscribe { view.showLoader() }
                .doOnUnsubscribe { view.hideLoader() }
                .subscribe({ view.showKeys(it) }, { view.showKeysError() })
    }

    fun pause() {
        subscription?.unsubscribe()
    }
}