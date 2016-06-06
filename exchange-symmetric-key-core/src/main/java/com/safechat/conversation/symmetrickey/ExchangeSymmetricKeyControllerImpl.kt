package com.safechat.conversation.symmetrickey

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyController
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyController.RetrieveResult.KEY_RETRIEVED
import rx.Observable
import rx.Observable.just

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
                .flatMap(doIfNotPresent({ postController.postKey(otherPublicKey) }))
    }

    private fun doIfNotPresent(onKeyNotPresent: () -> Observable<Unit>): (RetrieveResult) -> Observable<Unit> {
        return {
            if (it == KEY_RETRIEVED)
                just(Unit)
            else {
                onKeyNotPresent()
            }
        }
    }

    val onSuccess: (Unit) -> Unit = {
        view.complete()
    }

    val onError: (Throwable) -> Unit = {
        view.showError()
    }
}