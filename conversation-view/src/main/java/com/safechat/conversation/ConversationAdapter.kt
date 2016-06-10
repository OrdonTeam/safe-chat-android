package com.safechat.conversation

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.elpassion.android.commons.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.commons.recycler.ItemAdapter

class ConversationAdapter : BaseRecyclerViewAdapter() {

    fun add(messages: List<MessageItemAdapter>) {
        adapters.addAll(messages)
        notifyDataSetChanged()
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

}