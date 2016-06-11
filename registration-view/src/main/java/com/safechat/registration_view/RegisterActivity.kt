package com.safechat.registration_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.safechat.register.RegisterController
import com.safechat.register.RegisterView

class RegisterActivity : AppCompatActivity(), RegisterView {

    val loader by lazy { findViewById(R.id.register_loader)!! }
    val message by lazy { findViewById(R.id.register_message) as TextView }
    val registerController by lazy { registerControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view)
        registerController.onViewCreated()
    }


    override fun showRegisterLoader() {
        loader.visibility = View.VISIBLE
    }

    override fun hideRegisterLoader() {
        loader.visibility = View.GONE
    }

    override fun successLogIn() {
        openSelectConversation(this)
        finish()
    }

    override fun showKeyRegisterError() {
        message.text = "Log in error"
    }

    companion object {
        lateinit var registerControllerProvider: (RegisterActivity) -> RegisterController
        lateinit var openSelectConversation: (RegisterActivity) -> Unit
    }
}