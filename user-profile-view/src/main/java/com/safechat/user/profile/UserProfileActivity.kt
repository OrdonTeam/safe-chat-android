package com.safechat.user.profile

import android.support.v7.app.AppCompatActivity


class UserProfileActivity : AppCompatActivity() {

    companion object {
        lateinit var userProfileControllerProvider: () -> UserProfileController
    }
}
