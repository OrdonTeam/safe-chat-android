package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator,
        val registerService: RegisterService) {

    fun onViewCreated() {
        if(!registerRepository.isKeySaved()) {
            val newKey = keyGenerator.generateNewKey()
            registerRepository.saveNewKey(newKey)
            registerService.registerNewKey(newKey.public)
        }
        registerView.successLogIn()
    }
}