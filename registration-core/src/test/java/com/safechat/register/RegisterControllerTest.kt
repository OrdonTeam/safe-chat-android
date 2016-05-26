package com.safechat.register

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when` as on
import org.mockito.Mockito.*
import rx.Observable.error
import java.security.KeyPair
import java.security.PublicKey

class RegisterControllerTest {

    val newKey = KeyPair(mock(PublicKey::class.java), null)
    val registerView = mock(RegisterView::class.java)
    val registerRepository = mock(RegisterRepository::class.java)
    val keyGenerator = mock(KeyGenerator::class.java)
    val registerService = mock(RegisterService::class.java)
    val registerController = RegisterController(registerView, registerRepository, keyGenerator, registerService)

    @Before
    fun setUp() {
        on(registerService.registerNewKey(newKey.public)).thenReturn(error(RuntimeException()))
        on(keyGenerator.generateNewKey()).thenReturn(newKey)
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
        verify(keyGenerator, times(1)).generateNewKey()
    }

    @Test
    fun shouldNotGenerateNewKeyIfKeyAlreadyExists() {
        on(registerRepository.isKeySaved()).thenReturn(true)
        registerController.onViewCreated()
        verify(keyGenerator, never()).generateNewKey()
    }

    @Test
    fun shouldSaveNewKey() {
        registerController.onViewCreated()
        verify(registerRepository, times(1)).saveNewKey(newKey)
    }

    @Test
    fun shouldRegisterNewKeyOnServer() {
        registerController.onViewCreated()
        verify(registerService, times(1)).registerNewKey(newKey.public)
    }

    @Test
    fun shouldNotCallOnLogInWhenCallFails() {
        on(registerService.registerNewKey(newKey.public)).thenReturn(error(RuntimeException()))
        registerController.onViewCreated()
        verify(registerView, never()).successLogIn()
    }
}