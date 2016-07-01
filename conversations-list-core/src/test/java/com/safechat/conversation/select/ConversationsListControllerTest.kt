package com.safechat.conversation.select

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.safechat.message.Message
import org.junit.Before
import org.junit.Test

class ConversationsListControllerTest {

    val view = mock<ConversationsListView>()
    val repository = mock<ConversationsListRepository>()
    val controller = ConversationsListControllerImpl(repository, view)

    @Before
    fun setUp() {
        whenever(repository.getConversationsMessages()).thenReturn(emptyMap())
    }

    @Test
    fun shouldGetAllUserConversationsOnCreate() {
        controller.onCreate()
        verify(repository).getConversationsMessages()
    }

    @Test
    fun shouldShowUsersFromRepository() {
        val results = emptyMap<String, Message>()
        whenever(repository.getConversationsMessages()).thenReturn(results)
        controller.onCreate()
        verify(view).showConversations(results)
    }
}

