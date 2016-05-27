package com.safechat

import android.os.Bundle
import android.util.Log
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.SelectConversationViewImpl
import com.safechat.encryption.Encryptor.newKeyPair
import com.safechat.firebase.RegisterServiceImpl

class SelectConversationActivity : BaseActivity() {

    val loginView = SelectConversationViewImpl()
    val loginController = SelectConversationController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        findViewById(R.id.login)!!.setOnClickListener {
            RegisterServiceImpl().registerNewKey(newKeyPair().public).subscribe ({
                Log.e("Success","Success")
            })
        }
    }
}