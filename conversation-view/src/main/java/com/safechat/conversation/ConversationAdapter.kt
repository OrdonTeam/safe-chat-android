package com.safechat.conversation

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.elpassion.android.commons.recycler.BaseRecyclerViewAdapter
import com.elpassion.android.commons.recycler.ItemAdapter

class ConversationAdapter : BaseRecyclerViewAdapter() {

    fun add(messages: List<MessageItemAdapter>) {
        messages.forEach {
            add(it)
        }
    }

    private fun add(message: MessageItemAdapter) {
        removeIfAlreadyOnList(message.message.timestamp)
        adapters.add(message)
        adapters.sortBy { (it as MessageItemAdapter).message.timestamp }
        notifyDataSetChanged()
    }

    private fun removeIfAlreadyOnList(timestamp: Long) {
        adapters.firstOrNull { hasSameTimestamp(it, timestamp) }
                ?.run { adapters.remove(this) }
    }

    private fun hasSameTimestamp(it: ItemAdapter<out RecyclerView.ViewHolder>, timestamp: Long): Boolean {
        return (it as MessageItemAdapter).message.timestamp == timestamp
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