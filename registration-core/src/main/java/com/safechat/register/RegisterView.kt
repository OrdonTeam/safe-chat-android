package com.safechat.register

interface RegisterView {
    fun successLogIn()

    fun showKeyRegisterError()

    fun showRegisterLoader()

    fun hideRegisterLoader()
}