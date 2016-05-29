package com.safechat

import android.app.Activity
import com.safechat.encryption.KeyGeneratorImpl
import com.safechat.firebase.KeyListServiceImpl
import com.safechat.firebase.RegisterServiceImpl
import com.safechat.key.list.KeyListController
import com.safechat.key.list_view.KeyListViewImpl
import com.safechat.register.OnRegistrationCompletedListener
import com.safechat.register.RegisterController
import com.safechat.registration_view.RegisterViewImpl
import com.safechat.repository.RepositoryImpl

class AppController(activity: Activity) : OnRegistrationCompletedListener {

    val registerController by lazy {
        RegisterController(
                RegisterViewImpl(activity), RepositoryImpl(activity), KeyGeneratorImpl(), RegisterServiceImpl(), this)
    }

    val keyListController by lazy {
        KeyListController(KeyListViewImpl(activity), KeyListServiceImpl())
    }

    fun start() {
        registerController.onViewCreated()
    }

    fun pause() {
        keyListController.pause()
    }

    override fun onRegistrationCompleted() {
        keyListController.start()
    }
}