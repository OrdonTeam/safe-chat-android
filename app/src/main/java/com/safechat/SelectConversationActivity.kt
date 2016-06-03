package com.safechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.SelectConversationViewImpl
import com.safechat.firebase.users.UsersServiceImpl

class SelectConversationActivity : BaseActivity() {

    val controller by lazy {
        SelectConversationController(UsersServiceImpl(), SelectConversationViewImpl(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        controller.onCreate()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SelectConversationActivity::class.java))
        }
    }
}