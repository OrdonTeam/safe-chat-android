package com.safechat.register

import rx.Observable

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator,
        val registerService: RegisterService) {

    fun onViewCreated() {
        if (!registerRepository.isKeySaved()) {
            registerNewKey()
                    .doOnSubscribe { registerView.showRegisterLoader() }
                    .doOnTerminate { registerView.hideRegisterLoader() }
                    .subscribe({ registerView.successLogIn() }, { registerView.showKeyRegisterError() })
        } else {
            registerView.successLogIn()
        }
    }

    private fun registerNewKey(): Observable<Unit> {
        return keyGenerator
                .generateNewKey()
                .flatMap { newKey ->
                    registerService.registerNewKey(newKey.public).map { newKey }
                }
                .map {
                    registerRepository.saveNewKey(it)
                }
    }
}