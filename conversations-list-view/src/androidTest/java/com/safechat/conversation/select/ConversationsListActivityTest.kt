package com.safechat.conversation.select

import org.junit.Rule
import org.junit.Test

class ConversationsListActivityTest {

    val listController: ConversationsListController = object : ConversationsListController {
        override fun onResume() {
        }
    }

    @JvmField @Rule
    val rule = activityRule<ConversationsListActivity>() {
        ConversationsListActivity.conversationsListControllerProvider = { listController }
    }

    @Test
    fun shouldStartSelectConversationActivity() {

    }
}