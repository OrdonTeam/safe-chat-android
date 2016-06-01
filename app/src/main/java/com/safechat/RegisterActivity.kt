package com.safechat

import android.os.Bundle
import com.safechat.encryption.KeyGeneratorImpl
import com.safechat.firebase.KeyListServiceImpl
import com.safechat.firebase.RegisterServiceImpl
import com.safechat.key.list.KeyListController
import com.safechat.key.list_view.KeyListViewImpl
import com.safechat.register.OnRegistrationCompletedListener
import com.safechat.register.RegisterController
import com.safechat.registration_view.RegisterViewImpl
import com.safechat.repository.RepositoryImpl

class RegisterActivity : BaseActivity() {

    val appController by lazy { AppController(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_screen)
        appController.start()
    }

    override fun onPause() {
        appController.pause()
        super.onPause()
    }
}