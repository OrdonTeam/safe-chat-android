package com.safechat.conversation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.safechat.message.Message

class ConversationActivity : AppCompatActivity(), ConversationView {

    val controller by lazy { conversationControllerProvider(this) }
    val adapter = ConversationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation)
        val otherPublicKey = intent.getStringExtra(OTHER_PUBLIC_KEY)
        initToolbar(otherPublicKey)
        initMessageInput(otherPublicKey)
        initMessageContainer()
        controller.onCreated(otherPublicKey)
    }

    private fun initMessageContainer() {
        val recyclerView = findViewById(R.id.conversation_messages) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initMessageInput(otherPublicKey: String) {
        (findViewById(R.id.conversation_message) as TextView).setOnEditorActionListener { textView, i, keyEvent ->
            controller.onNewMessage(otherPublicKey, Message(textView.text.toString().trim(), true, false, System.currentTimeMillis()))
            textView.text = ""
            true
        }
    }

    private fun initToolbar(otherPublicKey: String?) {
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.title = otherPublicKey
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun showMessages(messages: List<com.safechat.message.Message>) {
        adapter.add(messages.map { ConversationAdapter.MessageItemAdapter(it) })
    }

    override fun showError() {
        (findViewById(R.id.conversation_error) as TextView).apply {
            visibility = View.VISIBLE
            text = "Error"
        }
    }

    override fun close() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            controller.onBackArrowClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private val OTHER_PUBLIC_KEY = "otherPublicKey"

        lateinit var conversationControllerProvider: (ConversationActivity) -> ConversationController

        val start: (Context, String) -> Unit = { context: Context, otherPublicKey: String ->
            context.startActivity(activityIntent(context, otherPublicKey))
        }

        fun activityIntent(context: Context, otherPublicKey: String) = Intent(context, ConversationActivity::class.java).apply { putExtra(OTHER_PUBLIC_KEY, otherPublicKey) }
    }
}