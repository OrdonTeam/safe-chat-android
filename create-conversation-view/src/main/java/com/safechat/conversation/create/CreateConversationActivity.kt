package com.safechat.conversation.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView

class CreateConversationActivity : AppCompatActivity(), CreateConversationView {

    val controller by lazy { createConversationControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_conversation)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setTitle(R.string.create_conversation_title)
        controller.onCreate()
        val suidInput = findViewById(R.id.unique_id_input) as TextView
        findViewById(R.id.create_conversation_button)!!.setOnClickListener {
            controller.onCreateConversationButtonClick(suidInput.text.toString())
        }
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

    override fun showUserFoundScreen(rsa: String) {
        showUserFoundScreen(this, rsa)
    }

    override fun showUserNotFound() {
        findViewById(R.id.user_not_found_error_message)!!.visibility = View.VISIBLE
    }

    companion object {

        lateinit var createConversationControllerProvider: (CreateConversationActivity) -> CreateConversationController
        lateinit var showUserFoundScreen: (Context, String) -> Unit
        fun start(context: Context) {
            context.startActivity(Intent(context, CreateConversationActivity::class.java))
        }
    }
}
