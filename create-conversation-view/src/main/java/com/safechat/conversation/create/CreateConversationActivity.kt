package com.safechat.conversation.create

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity

class CreateConversationActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CreateConversationActivity::class.java))
        }
    }
}
