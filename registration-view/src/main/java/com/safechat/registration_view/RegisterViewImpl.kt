package com.safechat.registration_view

import android.app.Activity
import android.view.View
import android.widget.TextView
import com.safechat.register.RegisterView

class RegisterViewImpl(activity: Activity) : RegisterView {

    val loader = activity.findViewById(R.id.register_loader)
    val messsage = activity.findViewById(R.id.register_message) as TextView

    override fun showRegisterLoader() {
        loader.visibility = View.VISIBLE
    }

    override fun hideRegisterLoader() {
        loader.visibility = View.GONE
    }

    override fun successLogIn() {
        messsage.text = "Log in successful"
    }

    override fun showKeyRegisterError() {
        messsage.text = "Log in error"
    }
}