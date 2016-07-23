package com.safechat.conversation.select

import android.support.v7.widget.RecyclerView
import android.view.View
import com.elpassion.android.commons.recycler.ItemAdapter

class SeparatorItemAdapter() : ItemAdapter<SeparatorItemAdapter.Holder>(R.layout.separator_item) {

    override fun onCreateViewHolder(itemView: View) = Holder(itemView)

    override fun onBindViewHolder(holder: Holder) = Unit

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
