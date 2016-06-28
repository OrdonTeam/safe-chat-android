package com.safechat.conversation.select

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable.error
import rx.Observable.just

class SelectConversationControllerTest {

    val view = mock<SelectConversationView>()
    val service = mock<ConversationListService>()
    val controller = SelectConversationControllerImpl(service, view)

    @Before
    fun setUp() {
        whenever(service.getConversations()).thenReturn(error(RuntimeException()))
    }

    @Test
    fun shouldGetAllUserConversationsOnCreate() {
        controller.onCreate()
        verify(service).getConversations()
    }

    @Test
    fun shouldShowUsersFromService() {
        val results = emptyList<UserRsaConversation>()
        whenever(service.getConversations()).thenReturn(just(results))
        controller.onCreate()
        verify(view).showUsers(results)
    }
}

