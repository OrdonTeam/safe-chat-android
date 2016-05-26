package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository) {

    fun onViewCreated() {
        registerRepository.isKeySaved()
        registerView.successLogIn()
    }
}