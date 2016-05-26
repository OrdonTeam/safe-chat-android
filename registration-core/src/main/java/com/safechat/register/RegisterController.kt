package com.safechat.register

class RegisterController(val registerView: RegisterView) {

    fun onViewCreated() {
        registerView.successLogIn()
    }
}