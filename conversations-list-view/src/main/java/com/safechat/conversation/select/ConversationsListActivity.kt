package com.safechat.conversation.select

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import android.widget.ViewFlipper
import com.elpassion.android.commons.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.commons.recycler.ItemAdapter
import com.safechat.message.Message


class ConversationsListActivity : AppCompatActivity(), ConversationsListView {

    val controller by lazy { conversationsListControllerProvider(this) }
    val contentFlipper by lazy { findViewById(R.id.conversation_list_flipper) as ViewFlipper }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        supportActionBar!!.setTitle(R.string.conversation_list_title)
        findViewById(R.id.conversation_menu)!!.setOnClickListener { onCreateConversationSelect(this) }
        controller.onCreate()
    }

    override fun showEmptyConversationsPlaceholder() {
        contentFlipper.show(R.id.empty_conversation_placeholder)
    }

    override fun showConversations(conversations: Map<String, Message>) {
        contentFlipper.show(R.id.conversation_select_list)
        val recycler = findViewById(R.id.conversation_select_list) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = BaseRecyclerViewAdapter(
                conversations.map { mapToItemAdapter(it.key, it.value) }
                        .flatMap { listOf(it, SeparatorItemAdapter()) }
        )
    }

    private fun mapToItemAdapter(user: String, message: Message): UserItemAdapter {
        return UserItemAdapter(user, message, onRsaSelected)
    }

    private val onRsaSelected: (String) -> Unit = { rsa ->
        onPublicKeySelect(this, rsa)
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

fun ViewFlipper.show(@IdRes viewId: Int) {
    val child = findViewById(viewId)
    displayedChild = indexOfChild(child)
}
