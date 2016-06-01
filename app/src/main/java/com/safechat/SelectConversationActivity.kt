package com.safechat

import android.content.Context
import android.content.Intent
import android.os.Bundle

class SelectConversationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SelectConversationActivity::class.java))
        }
    }
}