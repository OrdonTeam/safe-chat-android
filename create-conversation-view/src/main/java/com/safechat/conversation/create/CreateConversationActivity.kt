package com.safechat.conversation.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import kotlinx.android.synthetic.main.create_conversation.*

class CreateConversationActivity : AppCompatActivity(), CreateConversationView {

    val controller by lazy { createConversationControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_conversation)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setTitle(R.string.create_conversation_title)
        controller.onCreate()
    }

    override fun showShortestUniqueId(suid: String) {
        (findViewById(R.id.my_suid) as TextView).text = suid
    }

    override fun showError() {
        val coordinator = findViewById(R.id.create_conversation_coordinator) as CoordinatorLayout
        Snackbar.make(coordinator, R.string.create_conversation_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.try_again, { controller.onCreate() })
                .show()
    }

    companion object {

        lateinit var createConversationControllerProvider: (CreateConversationActivity) -> CreateConversationController

        fun start(context: Context) {
            context.startActivity(Intent(context, CreateConversationActivity::class.java))
        }
    }
}
