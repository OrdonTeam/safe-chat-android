package com.safechat

import android.os.Bundle
import com.safechat.encryption.KeyGeneratorImpl
import com.safechat.firebase.RegisterServiceImpl
import com.safechat.register.RegisterController
import com.safechat.registration_view.RegisterViewImpl
import com.safechat.repository.RepositoryImpl

class RegisterActivity : BaseActivity(), RegisterViewImpl.Navigator {
    val registerController by lazy {
        RegisterController(
                RegisterViewImpl(this, this), RepositoryImpl(this), KeyGeneratorImpl(), RegisterServiceImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_view)
        registerController.onViewCreated()
    }

    override fun openSelectConversation() {
        SelectConversationActivity.start(this)
    }
}