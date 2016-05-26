package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator) {

    fun onViewCreated() {
        if(!registerRepository.isKeySaved()) {
            val newKey = keyGenerator.generateNewKey()
            registerRepository.saveNewKey(newKey)
        }
        registerView.successLogIn()
    }
}