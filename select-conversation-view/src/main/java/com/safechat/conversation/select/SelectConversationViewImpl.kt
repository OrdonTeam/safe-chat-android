package com.safechat.conversation.select

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.elpassion.android.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.recycler.ItemAdapter

class SelectConversationViewImpl(activity: Activity) : SelectConversationView {

    val recycler = (activity.findViewById(R.id.conversation_select_list) as RecyclerView).apply {
        this.layoutManager = LinearLayoutManager(activity)
    }

    override fun showUsers(users: List<User>) {
        recycler.adapter = object : BaseRecyclerViewAdapter() {
            fun init() {
                adapters.addAll(users.map { UserItemAdapter(it) })
            }
        }.apply { init() }
    }

    class UserItemAdapter(val user: User) : ItemAdapter<Holder>(R.layout.user_item) {

        override fun onCreateViewHolder(itemView: View) = Holder(itemView)

        override fun onBindViewHolder(holder: Holder) {
            holder.rsa.text = user.rsa
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rsa = itemView as TextView
    }
}
