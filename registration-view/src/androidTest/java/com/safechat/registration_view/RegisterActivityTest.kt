package com.safechat.registration_view

import com.safechat.register.RegisterController
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class RegisterActivityTest {

    val controller = Mockito.mock(RegisterController::class.java)

    @JvmField @Rule
    val rule = activityRule<RegisterActivity>() {
        RegisterActivity.registerControllerProvider = { controller }
    }

    @Test
    fun shouldStartRegisterActivity() {
        Mockito.verify(controller).onViewCreated()
    }
}