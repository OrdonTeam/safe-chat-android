package com.safechat.user.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView


class UserProfileActivity : AppCompatActivity(), UserProfileView {

    val controller by lazy { userProfileControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_profile)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setTitle(R.string.user_profile_title)
        controller.onCreate()
    }

    override fun showShortestUniqueId(suid: String) {
        (findViewById(R.id.user_profile_suid) as TextView).text = suid
    }

    override fun showError() {
        val coordinator = findViewById(R.id.user_profile_coordinator) as CoordinatorLayout
        Snackbar.make(coordinator, R.string.user_profile_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, { controller.onCreate() })
                .show()
    }

    companion object {
        lateinit var userProfileControllerProvider: (UserProfileActivity) -> UserProfileController

        fun start(context: Context) {
            context.startActivity(Intent(context, UserProfileActivity::class.java))
        }
    }
}
