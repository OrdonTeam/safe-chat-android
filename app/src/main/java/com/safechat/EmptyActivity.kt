package com.safechat

import android.app.Activity
import android.content.Context
import android.content.Intent

class EmptyActivity : Activity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, EmptyActivity::class.java))
        }
    }
}