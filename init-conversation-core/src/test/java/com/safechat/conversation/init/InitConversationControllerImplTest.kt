package com.safechat.conversation.init

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import rx.Observable
import javax.crypto.SecretKey

class InitConversationControllerImplTest {

    val view = mock<InitConversationView>()
    val repository = mock<InitConversationRepository>()
    val service = mock<InitConversationService>()
    val keyGenerator = mock<InitConversationKeyGenerator>()
    val controller = InitConversationControllerImpl(view, repository, service, keyGenerator)

    @Test
    fun shouldCallOnCompleteWhenSymmetricKeyAlreadyExchanged() {
        stubRepositoryToReturn(true)
        controller.onCreate("rsa")
        verify(view).complete()
    }

    @Test
    fun shouldNotCallOnCompleteBeforeCallCompleted() {
        stubRepositoryToReturn(false)
        stubServiceToReturn(Observable.never())
        controller.onCreate("rsa")
        verify(view, never()).complete()
    }

    @Test
    fun shouldCallOnCompleteWhenCallCompleted() {
        stubRepositoryToReturn(false)
        stubServiceToReturn(Observable.just("symmetric-key"))
        controller.onCreate("rsa")
        verify(view).complete()
    }

    @Test
    fun shouldCallOnErrorWhenCallCompletedWithError() {
        stubRepositoryToReturn(false)
        stubServiceToReturn(Observable.error(RuntimeException()))
        controller.onCreate("rsa")
        verify(view).showError()
    }

    @Test
    fun shouldNotCallOnCompleteWhenKeyGenerationIsInProgress() {
        stubRepositoryToReturn(false)
        stubServiceToReturn(Observable.just(null))
        stubGeneratorToReturn(Observable.never())
        controller.onCreate("rsa")
        verify(view, never()).complete()
    }

    @Test
    fun shouldCallOnErrorWhenKeyGenerationFails() {
        stubRepositoryToReturn(false)
        stubServiceToReturn(Observable.just(null))
        stubGeneratorToReturn(Observable.error(RuntimeException()))
        controller.onCreate("rsa")
        verify(view).showError()
    }

    private fun stubRepositoryToReturn(isSaved: Boolean) {
        whenever(repository.containsSavedSymmetricKey("rsa")).thenReturn(isSaved)
    }

    private fun stubServiceToReturn(result: Observable<String?>?) {
        whenever(service.getEncryptedSymmetricKey("rsa")).thenReturn(result)
    }

    private fun stubGeneratorToReturn(secretKey: Observable<SecretKey>) {
        whenever(keyGenerator.generateSymmetricKey()).thenReturn(secretKey)
    }
}
