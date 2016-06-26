package com.safechat.user.profile

import org.junit.Rule
import org.junit.Test

class UserProfileActivityTest {

    val controller = object : UserProfileController{
        override fun onCreate() {
        }
    }

    @JvmField @Rule
    val rule = activityRule<UserProfileActivity>() {
        UserProfileActivity.userProfileControllerProvider = { controller }
    }

    @Test
    fun shouldStartUserProfileActivity() {

    }
}