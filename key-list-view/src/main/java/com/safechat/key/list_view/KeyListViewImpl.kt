package com.safechat.key.list_view

import android.app.Activity
import android.view.View
import android.widget.TextView
import com.safechat.key.list.KeyListView

class KeyListViewImpl(activity: Activity) : KeyListView {

    val loader = activity.findViewById(R.id.key_list_loader)
    val messsage = activity.findViewById(R.id.key_list_container) as TextView

    override fun showLoader() {
        loader.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader.visibility = View.GONE
    }

    override fun showKeys(it: List<String>) {
        messsage.text = it.joinToString("/n")
    }

    override fun showKeysError() {
        messsage.text = "key fetching error"
    }
}