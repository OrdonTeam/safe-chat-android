package com.safechat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.SelectConversationViewImpl
import com.safechat.conversation.select.User
import com.safechat.conversation.select.UsersService
import rx.Observable

class SelectConversationActivity : BaseActivity() {

    val controller by lazy {
        SelectConversationController(object : UsersService {
            override fun getUsers() = Observable.just(listOf(
                    User("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDIfUla_EeBCl--dO4kmsIUpyrmvr_qMWhNt8RtiT3e6qWxhpI2bFrzliDGHFy_jwuNfDDFaucquCi_iLni_BCIAYInGSnfjWjV_nrIL1EabLEbx8tJWZrQn5aob1no6t_A8V_bjd4NCQoShsHD9CMzxtA5AZGIE8dEHtbsUEsCwIDAQAB"),
                    User("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCulKXrz2QkY1PKX81LCk4sCwxSclMzkQPm6hWOdhs3M1-rULIZ7k84MojjLiOe506qwgFXlAZD2DNY7Ol2AAnok1KQ2CCNLdXMnsAteqD5uC7gMR7DE4aza1_ac6GWccIPbb5MJepqxd1HepQvb3xKmLW-LMlIGgRdYrZFdwlnEwIDAQAB")))
        }, SelectConversationViewImpl(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_conversation)
        controller.onCreate()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SelectConversationActivity::class.java))
        }
    }
}