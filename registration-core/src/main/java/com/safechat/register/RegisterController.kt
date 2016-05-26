package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator,
        val registerService: RegisterService) {

    fun onViewCreated() {
        registerView.showRegisterLoader()
        if (!registerRepository.isKeySaved()) {
            val newKey = keyGenerator.generateNewKey()
            registerRepository.saveNewKey(newKey)
            registerService.registerNewKey(newKey.public)
                    .doOnTerminate { registerView.hideRegisterLoader() }
                    .subscribe({ registerView.successLogIn() }, { registerView.showKeyRegisterError() })
        } else {
            registerView.successLogIn()
        }
    }
}