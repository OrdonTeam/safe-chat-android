package com.safechat.conversation.select

import org.junit.Rule
import org.junit.Test

class SelectConversationActivityTest {

    val controller: SelectConversationController = object : SelectConversationController{
        override fun onCreate() {
        }

        override fun onQuery(query: String) {
        }
    }

    @JvmField @Rule
    val rule = activityRule<SelectConversationActivity>() {
        SelectConversationActivity.selectConversationControllerProvider = { controller }
    }

    @Test
    fun shouldStartSelectConversationActivity() {

    }
}