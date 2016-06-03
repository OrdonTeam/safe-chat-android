package com.safechat.conversation.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SelectConversationActivity : AppCompatActivity() {

    val controller by lazy { selectConversationControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        controller.onCreate()
    }

    companion object {

        lateinit var selectConversationControllerProvider: (SelectConversationActivity) -> SelectConversationController

        fun start(context: Context) {
            context.startActivity(Intent(context, SelectConversationActivity::class.java))
        }
    }
}