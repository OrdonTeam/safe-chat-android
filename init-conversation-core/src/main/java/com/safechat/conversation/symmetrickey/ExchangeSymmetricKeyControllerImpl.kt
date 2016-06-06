package com.safechat.conversation.symmetrickey

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyController
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController
import rx.Observable

class ExchangeSymmetricKeyControllerImpl(
        val view: ExchangeSymmetricKeyView,
        val repository: ExchangeSymmetricKeyRepository,
        val retrieveController: RetrieveSymmetricKeyController,
        val postController: PostSymmetricKeyController) : ExchangeSymmetricKeyController {

    override fun onCreate(otherPublicKey: String) {
        if (repository.containsSymmetricKey(otherPublicKey)) {
            view.complete()
        } else {
            retrieveKey(otherPublicKey).subscribe(onSuccess, onError)
        }
    }

    private fun retrieveKey(otherPublicKey: String): Observable<Unit> {
        return retrieveController.retrieveKey(otherPublicKey)
                .flatMap { postController.postKey(otherPublicKey) }
                .map { Unit }
    }

    val onSuccess: (Unit) -> Unit = {
        view.complete()
    }

    val onError: (Throwable) -> Unit = {
        view.showError()
    }
}