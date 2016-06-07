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
        controller.onCreate("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDIfUla_EeBCl--dO4kmsIUpyrmvr_qMWhNt8RtiT3e6qWxhpI2bFrzliDGHFy_jwuNfDDFaucquCi_iLni_BCIAYInGSnfjWjV_nrIL1EabLEbx8tJWZrQn5aob1no6t_A8V_bjd4NCQoShsHD9CMzxtA5AZGIE8dEHtbsUEsCwIDAQAB")
    }

    override fun showLoader() {
        findViewById(R.id.exchange_loader)!!.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        findViewById(R.id.exchange_loader)!!.visibility = View.GONE
    }

    override fun complete() {
        (findViewById(R.id.exchange_message) as TextView).text = "POSTED"
    }

    override fun showError() {
        (findViewById(R.id.exchange_message) as TextView).text = "FAILED"
    }

    companion object {

        lateinit var exchangeSymmetricKeyControllerProvider: (ExchangeSymmetricKeyActivity) -> ExchangeSymmetricKeyController

        fun start(context: Context) {
            context.startActivity(Intent(context, ExchangeSymmetricKeyActivity::class.java))
        }
    }
}