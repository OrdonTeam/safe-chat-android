package com.safechat.conversation.symmetrickey.retrieve

import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_NOT_PRESENT
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_RETRIEVED
import rx.Observable

class RetrieveSymmetricKeyControllerImpl(
        val service: RetrieveSymmetricKeyService,
        val repository: RetrieveSymmetricKeyRepository,
        val decryptor: RetrieveSymmetricKeyDecryptor) : RetrieveSymmetricKeyController {

    override fun retrieveKey(otherPublicKey: String): Observable<RetrieveResult> {
        val myPublicKey = repository.getPublicKeyString()
        return service.retrieveSymmetricKey(myPublicKey, otherPublicKey)
                .flatMap {
                    if (it == null) {
                        Observable.just(KEY_NOT_PRESENT)
                    } else {
                        saveKey(otherPublicKey, it)
                    }
                }
    }

    private fun saveKey(otherPublicKey: String, encryptedSymmetricKey: String): Observable<RetrieveResult> {
        val myPrivateKey = repository.getPrivateKeyString()
        return decryptor.decryptSymmetricKey(otherPublicKey, myPrivateKey, encryptedSymmetricKey)
                .map { repository.saveDecryptedSymmetricKey(otherPublicKey, it) }
                .map { KEY_RETRIEVED }
    }
}