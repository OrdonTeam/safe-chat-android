package com.safechat

import android.os.Bundle
import com.safechat.login.LoginController
import com.safechat.login.LoginViewImpl

class LoginActivity : BaseActivity() {

    val loginView = LoginViewImpl()
    val loginController = LoginController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }
}