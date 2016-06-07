package com.safechat.conversation.select

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.elpassion.android.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.recycler.ItemAdapter

class SelectConversationActivity : AppCompatActivity(), SelectConversationView {

    val controller by lazy { selectConversationControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        controller.onCreate()
    }

    override fun showUsers(users: List<User>) {
        val recycler = findViewById(R.id.conversation_select_list) as RecyclerView
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = object : BaseRecyclerViewAdapter() {
            fun init() {
                adapters.addAll(users.map { mapToItemAdapter(it) })
            }

        }.apply { init() }
    }

    private fun mapToItemAdapter(it: User): UserItemAdapter {
        return UserItemAdapter(it, onRsaSelected)
    }

    private val onRsaSelected: (String) -> Unit = { rsa ->
        onPublicKeySelect(this, rsa)
    }

    class UserItemAdapter(val user: User, val onRsaSelected: (String) -> Unit) : ItemAdapter<Holder>(R.layout.user_item) {

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

        fun start(context: Context) {
            context.startActivity(Intent(context, SelectConversationActivity::class.java))
        }
    }
}