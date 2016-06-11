package com.safechat.registration_view

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.safechat.register.RegisterController
import com.safechat.register.RegisterView

class RegisterActivity : AppCompatActivity(), RegisterView {

    val loader by lazy { findViewById(R.id.register_loader)!! }
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
        val coordinator = findViewById(R.id.register_coordinator) as CoordinatorLayout
        Snackbar.make(coordinator, R.string.register_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, { registerController.onViewCreated() })
                .show()
    }

    companion object {
        lateinit var registerControllerProvider: (RegisterActivity) -> RegisterController
        lateinit var openSelectConversation: (RegisterActivity) -> Unit
    }
}