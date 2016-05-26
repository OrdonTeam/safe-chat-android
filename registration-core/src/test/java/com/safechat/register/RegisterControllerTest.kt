package com.safechat.register

import org.junit.Test
import org.mockito.Mockito.*

class RegisterControllerTest {

    val registerView = mock(RegisterView::class.java)
    val registerRepository = mock(RegisterRepository::class.java)

    @Test
    fun shouldCallOnLogInWhenKeyAlreadyExists() {
        RegisterController(registerView, registerRepository).onViewCreated()
        verify(registerView, times(1)).successLogIn()
    }

    @Test
    fun shouldCheckIfKeyExists() {
        RegisterController(registerView, registerRepository).onViewCreated()
        verify(registerRepository, times(1)).isKeySaved()
    }
}