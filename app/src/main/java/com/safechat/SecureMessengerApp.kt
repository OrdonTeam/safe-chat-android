package com.safechat

import android.app.Application
import com.safechat.conversation.select.SelectConversationActivity
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.SelectConversationViewImpl
import com.safechat.encryption.KeyGeneratorImpl
import com.safechat.firebase.register.RegisterServiceImpl
import com.safechat.firebase.users.UsersServiceImpl
import com.safechat.register.RegisterController
import com.safechat.registration_view.RegisterActivity
import com.safechat.registration_view.RegisterViewImpl
import com.safechat.repository.RepositoryImpl

class SecureMessengerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        SelectConversationActivity.selectConversationControllerProvider = {
            SelectConversationController(UsersServiceImpl(), SelectConversationViewImpl(it))
        }
        RegisterActivity.registerControllerProvider = {
            RegisterController(RegisterViewImpl(it, it), RepositoryImpl(it), KeyGeneratorImpl(), RegisterServiceImpl())
        }
        RegisterActivity.openSelectConversation = {
            SelectConversationActivity.start(it)
        }
    }
}