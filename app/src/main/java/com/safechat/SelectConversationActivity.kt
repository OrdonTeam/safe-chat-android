package com.safechat

import android.os.Bundle
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.SelectConversationViewImpl

class SelectConversationActivity : BaseActivity() {

    val loginView = SelectConversationViewImpl()
    val loginController = SelectConversationController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
    }
}