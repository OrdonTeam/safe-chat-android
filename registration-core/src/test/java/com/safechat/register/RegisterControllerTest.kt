package com.safechat.register

import org.junit.Test
import org.mockito.Mockito.`when` as on
import org.mockito.Mockito.*

class RegisterControllerTest {

    val registerView = mock(RegisterView::class.java)
    val registerRepository = mock(RegisterRepository::class.java)
    val keyGenerator = mock(KeyGenerator::class.java)
    val registerController = RegisterController(registerView, registerRepository, keyGenerator)


    @Test
    fun shouldCallOnLogInWhenKeyAlreadyExists() {
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
        on(registerRepository.isKeySaved()).thenReturn(false)
        registerController.onViewCreated()
        verify(keyGenerator, times(1)).generateNewKey()
    }
}