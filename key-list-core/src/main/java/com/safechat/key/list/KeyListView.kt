package com.safechat.key.list

interface KeyListView {
    fun showLoader()

    fun hideLoader()

    fun showKeys(it: List<String>)

    fun showKeysError()
}