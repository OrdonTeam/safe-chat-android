package com.safechat.conversation.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.elpassion.android.commons.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.commons.recycler.ItemAdapter

class SelectConversationActivity : AppCompatActivity(), SelectConversationView {

    val controller by lazy { selectConversationControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        getSupportActionBar()!!.setTitle(R.string.select_conversation_title)
        controller.onCreate()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu.findItem(R.id.show_info).setOnMenuItemClickListener { onMenuInfoSelect(this); true }
        return true
    }

    override fun showUsers(users: List<com.safechat.user.service.User>) {
        val recycler = findViewById(R.id.conversation_select_list) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = BaseRecyclerViewAdapter(users.map { mapToItemAdapter(it) })
    }

    private fun mapToItemAdapter(it: com.safechat.user.service.User): UserItemAdapter {
        return UserItemAdapter(it, onRsaSelected)
    }

    private val onRsaSelected: (String) -> Unit = { rsa ->
        onPublicKeySelect(this, rsa)
    }

    class UserItemAdapter(val user: com.safechat.user.service.User, val onRsaSelected: (String) -> Unit) : ItemAdapter<Holder>(R.layout.user_item) {

        override fun onCreateViewHolder(itemView: View) = Holder(itemView)

        override fun onBindViewHolder(holder: Holder) {
            holder.rsa.text = user.rsa
            holder.itemView.setOnClickListener {
                onRsaSelected(user.rsa)
            }
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rsa = itemView as TextView
    }

    companion object {

        lateinit var selectConversationControllerProvider: (SelectConversationActivity) -> SelectConversationController
        lateinit var onPublicKeySelect: (Context, String) -> Unit
        lateinit var onMenuInfoSelect: (Context) -> Unit

        fun start(context: Context) {
            context.startActivity(Intent(context, SelectConversationActivity::class.java))
        }
    }
}