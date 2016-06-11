package com.safechat.registration_view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
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

    @Test
    fun shouldShowError() {
        rule.activity.showKeyRegisterError()
        Thread.sleep(100)
        onView(withText(R.string.register_error)).check(matches(isDisplayed()))
    }
}