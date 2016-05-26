package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator) {

    fun onViewCreated() {
        if(!registerRepository.isKeySaved()) {
            keyGenerator.generateNewKey()
        }
        registerView.successLogIn()
    }
}