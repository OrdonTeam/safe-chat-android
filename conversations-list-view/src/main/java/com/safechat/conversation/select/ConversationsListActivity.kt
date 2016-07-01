package com.safechat.conversation.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.elpassion.android.commons.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.commons.recycler.ItemAdapter
import com.safechat.message.Message

class ConversationsListActivity : AppCompatActivity(), ConversationsListView {

    val controller by lazy { conversationsListControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setTitle(R.string.select_conversation_title)
        findViewById(R.id.create_conversation)!!.setOnClickListener { onCreateConversationSelect(this) }
        controller.onCreate()
    }

    override fun showConversations(conversations: Map<String, Message>) {
        val recycler = findViewById(R.id.conversation_select_list) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = BaseRecyclerViewAdapter(conversations.map { mapToItemAdapter(it.key, it.value) })
    }

    private fun mapToItemAdapter(user: String, message: Message): UserItemAdapter {
        return UserItemAdapter(user, message, onRsaSelected)
    }

    private val onRsaSelected: (String) -> Unit = { rsa ->
        onPublicKeySelect(this, rsa)
    }

    class UserItemAdapter(val user: String,
                          val messsage: Message,
                          val onRsaSelected: (String) -> Unit) : ItemAdapter<Holder>(R.layout.user_item) {

        override fun onCreateViewHolder(itemView: View) = Holder(itemView)

        override fun onBindViewHolder(holder: Holder) {
            holder.rsa.text = user
            holder.itemView.setOnClickListener {
                onRsaSelected(user)
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rsa = itemView as TextView
    }

    companion object {

        lateinit var conversationsListControllerProvider: (ConversationsListActivity) -> ConversationsListController
        lateinit var onPublicKeySelect: (Context, String) -> Unit
        lateinit var onCreateConversationSelect: (Context) -> Unit

        fun start(context: Context) {
            context.startActivity(Intent(context, ConversationsListActivity::class.java))
        }
    }
}