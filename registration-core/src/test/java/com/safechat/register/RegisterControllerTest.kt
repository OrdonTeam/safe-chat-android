package com.safechat.register

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable.error
import rx.Observable.just
import org.mockito.Mockito.`when` as on

class RegisterControllerTest {

    val newKey = KeyPairString("publicKey", "privateKey")
    val registerView = mock(RegisterView::class.java)
    val registerRepository = mock(RegisterRepository::class.java)
    val keyGenerator = mock(KeyGenerator::class.java)
    val registerService = mock(RegisterService::class.java)
    val registerController = RegisterControllerImpl(registerView, registerRepository, keyGenerator, registerService)

    @Before
    fun setUp() {
        on(registerService.registerNewKey(newKey.publicKey)).thenReturn(error(RuntimeException()))
        on(keyGenerator.generateNewKeyPair()).thenReturn(just(newKey))
    }

    @Test
    fun shouldCallOnLogInWhenKeyAlreadyExists() {
        on(registerRepository.isKeySaved()).thenReturn(true)
        registerController.onViewCreated()
        verify(registerView, times(1)).successLogIn()
    }

    @Test
    fun shouldCheckIfKeyExists() {
        registerController.onViewCreated()
        verify(registerRepository, times(1)).isKeySaved()
    }

    @Test
    fun shouldGenerateNewKeyIfKeyDoesNotExist() {
        registerController.onViewCreated()
        verify(keyGenerator, times(1)).generateNewKeyPair()
    }

    @Test
    fun shouldNotGenerateNewKeyIfKeyAlreadyExists() {
        on(registerRepository.isKeySaved()).thenReturn(true)
        registerController.onViewCreated()
        verify(keyGenerator, never()).generateNewKeyPair()
    }

    @Test
    fun shouldSaveNewKey() {
        on(registerService.registerNewKey(newKey.publicKey)).thenReturn(just(Unit))
        registerController.onViewCreated()
        verify(registerRepository, times(1)).saveNewKey(newKey)
    }

    @Test
    fun shouldRegisterNewKeyOnServer() {
        registerController.onViewCreated()
        verify(registerService, times(1)).registerNewKey(newKey.publicKey)
    }

    @Test
    fun shouldNotCallOnLogInWhenCallFails() {
        on(registerService.registerNewKey(newKey.publicKey)).thenReturn(error(RuntimeException()))
        registerController.onViewCreated()
        verify(registerView, never()).successLogIn()
    }

    @Test
    fun shouldShowKeyRegisterErrorWhenCallFails() {
        on(registerService.registerNewKey(newKey.publicKey)).thenReturn(error(RuntimeException()))
        registerController.onViewCreated()
        verify(registerView, times(1)).showKeyRegisterError()
    }

    @Test
    fun shouldShowLoader() {
        registerController.onViewCreated()
        verify(registerView, times(1)).showRegisterLoader()
    }

    @Test
    fun shouldHideLoader() {
        registerController.onViewCreated()
        verify(registerView, times(1)).hideRegisterLoader()
    }

    @Test
    fun shouldNotShowLoaderWhenKeyAlreadyExists() {
        on(registerRepository.isKeySaved()).thenReturn(true)
        registerController.onViewCreated()
        verify(registerView, never()).showRegisterLoader()
        verify(registerView, never()).hideRegisterLoader()
    }

    @Test
    fun shouldNotSaveKeyIfServiceFails() {
        on(registerService.registerNewKey(newKey.publicKey)).thenReturn(error(RuntimeException()))
        registerController.onViewCreated()
        verify(registerRepository, never()).saveNewKey(newKey)
    }
}