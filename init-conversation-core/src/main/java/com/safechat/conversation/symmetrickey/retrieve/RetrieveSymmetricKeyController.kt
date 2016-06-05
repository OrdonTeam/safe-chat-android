package com.safechat.conversation.symmetrickey.retrieve

import rx.Observable

class RetrieveSymmetricKeyController(
        val service: RetrieveSymmetricKeyService,
        val repository: RetrieveSymmetricKeyRepository,
        val decryptor: RetrieveSymmetricKeyDecryptor) {

    fun retrieveKey(otherPublicKey: String): Observable<RetrieveResult> {
        val myPublicKey = repository.getPublicKeyString()
        return service.retrieveSymmetricKey(myPublicKey, otherPublicKey)
                .flatMap {
                    if (it == null) {
                        Observable.just(RetrieveResult.KEY_NOT_PRESENT)
                    } else {
                        saveKey(otherPublicKey, it)
                    }
                }
    }

    private fun saveKey(otherPublicKey: String, encryptedSymmetricKey: String): Observable<RetrieveResult> {
        val myPrivateKey = repository.getPrivateKeyString()
        return decryptor.decryptSymmetricKey(otherPublicKey, myPrivateKey, encryptedSymmetricKey)
                .map { RetrieveResult.KEY_RETRIEVED }
    }

    enum class RetrieveResult {
        KEY_NOT_PRESENT, KEY_RETRIEVED
    }
}