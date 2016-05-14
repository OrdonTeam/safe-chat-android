package com.safechat

import android.os.Bundle
import com.safechat.login.LoginViewImpl

class LoginActivity : BaseActivity() {

    val loginView = LoginViewImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    }
}