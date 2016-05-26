package com.safechat.register

import org.junit.Test
import org.mockito.Mockito.*

class RegisterControllerTest {

    val registerView = mock(RegisterView::class.java)

    @Test
    fun shouldCallOnLogInWhenKeyAlreadyExists() {
        RegisterController(registerView).onViewCreated()
        verify(registerView, times(1)).successLogIn()
    }
}