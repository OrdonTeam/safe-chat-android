package com.safechat.key.list

import rx.Observable

interface KeyListService {
    fun keys(): Observable<List<String>>
}