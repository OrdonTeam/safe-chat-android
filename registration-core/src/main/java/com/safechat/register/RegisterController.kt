package com.safechat.register

import rx.Observable

class RegisterController(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator,
        val registerService: RegisterService,
        val onRegistrationCompletedListener: OnRegistrationCompletedListener) {

    fun onViewCreated() {
        if (!registerRepository.isKeySaved()) {
            registerNewKey()
                    .doOnSubscribe { registerView.showRegisterLoader() }
                    .doOnTerminate { registerView.hideRegisterLoader() }
                    .subscribe({ onRegistrationSuccess() }, { registerView.showKeyRegisterError() })
        } else {
            onRegistrationSuccess()
        }
    }

    private fun onRegistrationSuccess() {
        registerView.successLogIn()
        onRegistrationCompletedListener.onRegistrationCompleted()
    }

    private fun registerNewKey(): Observable<Unit> {
        return keyGenerator.generateNewKey()
                .flatMap { newKey ->
                    registerRepository.saveNewKey(newKey)
                    registerService.registerNewKey(newKey.public)
                }
    }
}