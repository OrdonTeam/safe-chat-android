package com.safechat.register

import rx.Observable

class RegisterControllerImpl(
        val registerView: RegisterView,
        val registerRepository: RegisterRepository,
        val keyGenerator: KeyGenerator,
        val registerService: RegisterService) : RegisterController {

    override fun onViewCreated() {
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
                .generateNewKeyPair()
                .flatMap { newKey ->
                    registerService.registerNewKey(newKey.publicKey).map { newKey }
                }
                .map {
                    registerRepository.saveNewKey(it)
                }
    }

}