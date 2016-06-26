package com.safechat.user.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView


class UserProfileActivity : AppCompatActivity(), UserProfileView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setTitle(R.string.user_profile_title)
        val controller = userProfileControllerProvider(this)
        controller.onCreate()
    }

    override fun showShortestUniqueId(suid: String) {
        (findViewById(R.id.user_profile_suid) as TextView).text = suid
    }

    companion object {
        lateinit var userProfileControllerProvider: (UserProfileActivity) -> UserProfileController

        fun start(context: Context) {
            context.startActivity(Intent(context, UserProfileActivity::class.java))
        }
    }
}
