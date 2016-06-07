package com.safechat.conversation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.elpassion.android.commons.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.commons.recycler.ItemAdapter

class ConversationActivity : AppCompatActivity(), ConversationView {

    val controller by lazy { conversationControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversation)
        controller.onCreated(intent.getStringExtra(OTHER_PUBLIC_KEY))
        (findViewById(R.id.conversation_message) as TextView).setOnEditorActionListener { textView, i, keyEvent ->
            controller.onNewMessage(intent.getStringExtra(OTHER_PUBLIC_KEY), Message(textView.text.toString().trim(), true))
            true
        }
    }

    override fun showMessages(messages: List<Message>) {
        val recyclerView = findViewById(R.id.conversation_messages) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BaseRecyclerViewAdapter(messages.map { MessageItemAdapter(it) })
    }

    class MessageItemAdapter(val message: Message) : ItemAdapter<Holder>(R.layout.message) {
        override fun onCreateViewHolder(itemView: View) = Holder(itemView)

        override fun onBindViewHolder(holder: Holder) {
            holder.message.text = message.text
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val message = itemView as TextView
    }

    override fun showError() {
        (findViewById(R.id.conversation_error) as TextView).apply {
            visibility = View.VISIBLE
            text = "Error"
        }
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