package com.safechat.conversation.symmetrickey.post

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable.just
import rx.observers.TestSubscriber

class PostSymmetricKeyControllerTest {

    val service = mock<PostSymmetricKeyService>()
    val repository = mock<PostSymmetricKeyRepository>()
    val encryptor = mock<PostSymmetricKeyEncryptor>()
    val controller = PostSymmetricKeyControllerImpl(encryptor, repository, service)
    val subscriber = TestSubscriber<Unit>()

    @Before
    fun setUp() {
        stubGenerator()
        stubRepository()
        stubService()
    }

    @Test
    fun shouldGenerateKey() {
        startController()
        verify(encryptor).generateSymmetricKey()
    }

    @Test
    fun shouldSaveGeneratedKey() {
        startController()
        verify(repository).saveDecryptedSymmetricKey("otherPublicKey", "newSymmetricKey")
    }

    @Test
    fun shouldEncryptGeneratedKey() {
        startController()
        verify(encryptor).encryptSymmetricKey("otherPublicKey", "myPrivateKey", "newSymmetricKey")
    }

    @Test
    fun shouldPostEncryptedKey() {
        startController()
        verify(service).postSymmetricKey("myPublicKey", "otherPublicKey", "encryptedSymmetricKey")
    }

    private fun startController() {
        controller.postKey("otherPublicKey").subscribe(subscriber)
    }

    private fun stubGenerator() {
        whenever(encryptor.encryptSymmetricKey("otherPublicKey", "myPrivateKey", "newSymmetricKey")).thenReturn(just("encryptedSymmetricKey"))
        whenever(encryptor.generateSymmetricKey()).thenReturn(just("newSymmetricKey"))
    }

    private fun stubRepository() {
        whenever(repository.getPublicKeyString()).thenReturn("myPublicKey")
        whenever(repository.getPrivateKeyString()).thenReturn("myPrivateKey")
    }

    private fun stubService() {
        whenever(service.postSymmetricKey("myPublicKey", "otherPublicKey", "encryptedSymmetricKey")).thenReturn(just(Unit))
    }
}