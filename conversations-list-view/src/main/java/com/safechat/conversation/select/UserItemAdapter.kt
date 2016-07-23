package com.safechat.conversation.select

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.elpassion.android.commons.recycler.ItemAdapter
import com.safechat.message.Message

class UserItemAdapter(val user: String,
                      val message: Message,
                      val onRsaSelected: (String) -> Unit) : ItemAdapter<UserItemAdapter.Holder>(R.layout.conversation_item) {

    override fun onCreateViewHolder(itemView: View) = Holder(itemView)

    override fun onBindViewHolder(holder: Holder) {
        holder.nameView.text = user
        holder.dateView.text = message.timestamp.toString()
        holder.conversationColorView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.red))
        holder.lastMessageView.text = message.text
        holder.itemView.setOnClickListener {
            onRsaSelected(user)
        }
        if (!message.isRead) {
            holder.nameView.setTypeface(null, Typeface.BOLD)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray_background_color))
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView by lazy { itemView.findViewById(R.id.conversation_item_name) as TextView }
        val conversationColorView by lazy { itemView.findViewById(R.id.conversation_item_color) }
        val dateView by lazy { itemView.findViewById(R.id.conversation_item_date) as TextView }
        val lastMessageView by lazy { itemView.findViewById(R.id.conversation_item_last_message) as TextView }
    }
}