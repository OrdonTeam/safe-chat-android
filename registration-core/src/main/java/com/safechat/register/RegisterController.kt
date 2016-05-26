package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator) {

    fun onViewCreated() {
        keyGenerator.generateNewKey()
        registerRepository.isKeySaved()
        registerView.successLogIn()
    }
}