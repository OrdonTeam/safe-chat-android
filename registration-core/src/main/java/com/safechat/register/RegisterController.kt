package com.safechat.register

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator,
        val registerService: RegisterService) {

    fun onViewCreated() {
        if (!registerRepository.isKeySaved()) {
            registerView.showRegisterLoader()
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