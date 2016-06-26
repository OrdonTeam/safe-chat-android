package com.safechat.user.profile

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule
import java.util.*

inline fun <reified T : Activity> activityRule(intent: Intent? = null, crossinline beforeActivityFunction: () -> Unit) = object : ActivityTestRule<T>(T::class.java) {
    override fun getActivityIntent() = intent ?: super.getActivityIntent()

    override fun beforeActivityLaunched() = beforeActivityFunction()
}