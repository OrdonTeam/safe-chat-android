package com.safechat.registration_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.safechat.register.RegisterController

class RegisterActivity : AppCompatActivity(), RegisterViewImpl.Navigator {

    val registerController by lazy { registerControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view)
        registerController.onViewCreated()
    }

    override fun openSelectConversation() {
        openSelectConversation(this)
    }

    companion object {
        lateinit var registerControllerProvider: (RegisterActivity) -> RegisterController
        lateinit var openSelectConversation: (RegisterActivity) -> Unit
    }
}