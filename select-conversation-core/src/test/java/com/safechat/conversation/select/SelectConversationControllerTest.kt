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
    val service = mock<UsersService>()
    val controller = SelectConversationController(service, view)

    @Before
    fun setUp() {
        whenever(service.getUsers()).thenReturn(error(RuntimeException()))
    }

    @Test
    fun shouldGetAllUsersOnCreate() {
        controller.onCreate()
        verify(service).getUsers()
    }

    @Test
    fun shouldShowUsersFromService() {
        val results = emptyList<User>()
        whenever(service.getUsers()).thenReturn(just(results))
        controller.onCreate()
        verify(view).showUsers(results)
    }

    @Test
    fun shouldFilterOutResultsByGivenQuery() {
        val user = User("A")
        whenever(service.getUsers()).thenReturn(just(listOf(user, User("B"))))
        controller.onCreate()

        controller.onQuery("A")

        verify(view).showUsers(listOf(user))
    }
}