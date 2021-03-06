package com.safechat.conversation.select

import android.app.Activity
import android.content.Intent
import android.support.test.rule.ActivityTestRule

inline fun <reified T : Activity> activityRule(intent: Intent? = null) = object : ActivityTestRule<T>(T::class.java) {
    override fun getActivityIntent() = intent ?: super.getActivityIntent()
}

inline fun <reified T : Activity> activityRule(intent: Intent? = null, crossinline beforeActivityFunction: () -> Unit) = object : ActivityTestRule<T>(T::class.java) {
    override fun getActivityIntent() = intent ?: super.getActivityIntent()

    override fun beforeActivityLaunched() = beforeActivityFunction()
}