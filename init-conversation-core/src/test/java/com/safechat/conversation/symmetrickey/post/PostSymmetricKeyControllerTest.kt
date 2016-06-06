package com.safechat.conversation.symmetrickey.post

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import rx.Observable.error
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

    @Test
    fun shouldNotSaveKeyIfPostFails() {
        whenever(service.postSymmetricKey(any(), any(), any())).thenReturn(error(RuntimeException()))
        startController()
        verify(repository, never()).saveDecryptedSymmetricKey(any(), any())
    }

    @Test
    fun shouldReturnErrorIfPostFails() {
        val runtimeException = RuntimeException()
        whenever(service.postSymmetricKey(any(), any(), any())).thenReturn(error(runtimeException))
        startController()
        subscriber.assertError(runtimeException)
    }

    @Test
    fun shouldReturnErrorIfGenerationFails() {
        val runtimeException = RuntimeException()
        whenever(encryptor.generateSymmetricKey()).thenReturn(error(runtimeException))
        startController()
        subscriber.assertError(runtimeException)
    }

    @Test
    fun shouldReturnErrorIfEncryptionFails() {
        val runtimeException = RuntimeException()
        whenever(encryptor.encryptSymmetricKey(any(), any(), any())).thenReturn(error(runtimeException))
        startController()
        subscriber.assertError(runtimeException)
    }

    @Test
    fun shouldReturnUnitIfEverythingGoesRight() {
        startController()
        subscriber.assertValue(Unit)
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