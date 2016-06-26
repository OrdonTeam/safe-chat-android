package com.safechat.conversation.select

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.Observable.error
import rx.Observable.just
import rx.Subscriber

class SelectConversationControllerTest {

    val view = mock<SelectConversationView>()
    val service = mock<com.safechat.user.service.UsersService>()
    val repository = mock<SelectConversationRepository>()
    val controller = SelectConversationControllerImpl(service, view, repository)

    @Before
    fun setUp() {
        whenever(service.getUsers()).thenReturn(error(RuntimeException()))
        whenever(repository.getPublicKeyString()).thenReturn("my_public_key")
    }

    @Test
    fun shouldGetAllUsersOnCreate() {
        controller.onCreate()
        verify(service).getUsers()
    }

    @Test
    fun shouldShowUsersFromService() {
        val results = emptyList<com.safechat.user.service.User>()
        whenever(service.getUsers()).thenReturn(just(results))
        controller.onCreate()
        verify(view).showUsers(results)
    }

    @Test
    fun shouldFilterOutResultsByGivenQuery() {
        val user = com.safechat.user.service.User("A")
        whenever(service.getUsers()).thenReturn(just(listOf(user, com.safechat.user.service.User("B"))))
        controller.onCreate()

        controller.onQuery("A")

        verify(view).showUsers(listOf(user))
    }

    @Test
    fun shouldShowFilteredListWhenCallEndsAfterQueryTyped() {
        val holder = ObservableHolder(listOf(com.safechat.user.service.User("A"), com.safechat.user.service.User("B")))
        whenever(service.getUsers()).thenReturn(holder.observable)
        controller.onCreate()

        controller.onQuery("A")
        holder.release()

        verify(view).showUsers(listOf(com.safechat.user.service.User("A")))
    }

    @Test
    fun shouldFilterSelfRsaOut() {
        val results = listOf(com.safechat.user.service.User("my_public_key"))
        whenever(service.getUsers()).thenReturn(just(results))
        controller.onCreate()
        verify(view).showUsers(emptyList())
    }

    class ObservableHolder<T>(val value:T) {
        private lateinit var subscriber: Subscriber<in T>

        val observable = Observable.create<T> {
            subscriber = it
            it.onStart()
        }

        fun release() {
            subscriber.onNext(value)
            subscriber.onCompleted()
        }
    }
}