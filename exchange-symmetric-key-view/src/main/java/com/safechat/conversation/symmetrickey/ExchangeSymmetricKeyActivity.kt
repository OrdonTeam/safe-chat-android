package com.safechat.conversation.symmetrickey

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView

class ExchangeSymmetricKeyActivity : AppCompatActivity(), ExchangeSymmetricKeyView {

    val controller by lazy { exchangeSymmetricKeyControllerProvider(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.exchange_symmetric_key_view)
        controller.onCreate(intent.getStringExtra(OTHER_PUBLIC_KEY))
    }

    override fun showLoader() {
        findViewById(R.id.exchange_loader)!!.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        findViewById(R.id.exchange_loader)!!.visibility = View.GONE
    }

    override fun complete() {
        onKeyExchange(this, intent.getStringExtra(OTHER_PUBLIC_KEY))
        finish()
    }

    override fun showError() {
        (findViewById(R.id.exchange_message) as TextView).text = "FAILED"
    }

    companion object {

        private val OTHER_PUBLIC_KEY = "otherPublicKey"

        lateinit var exchangeSymmetricKeyControllerProvider: (ExchangeSymmetricKeyActivity) -> ExchangeSymmetricKeyController
        lateinit var onKeyExchange: (Context, String) -> Unit

        val start: (Context, String) -> Unit = { context: Context, otherPublicKey: String ->
            context.startActivity(activityIntent(context, otherPublicKey))
        }

        fun activityIntent(context: Context, otherPublicKey: String) = Intent(context, ExchangeSymmetricKeyActivity::class.java).apply { putExtra(OTHER_PUBLIC_KEY, otherPublicKey) }
    }
}