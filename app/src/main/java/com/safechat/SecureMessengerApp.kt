package com.safechat

import android.app.Application
import com.safechat.conversation.select.SelectConversationActivity
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.SelectConversationViewImpl
import com.safechat.firebase.users.UsersServiceImpl

class SecureMessengerApp : Application() {

    override fun onCreate() {
        super.onCreate()

        SelectConversationActivity.selectConversationControllerProvider = { it ->
            SelectConversationController(UsersServiceImpl(), SelectConversationViewImpl(it))
        }
    }
}