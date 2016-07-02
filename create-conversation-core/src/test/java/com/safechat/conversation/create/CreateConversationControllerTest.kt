package com.safechat.conversation.create

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import rx.Observable

class CreateConversationControllerTest {

    val service = mock<CreateConversationService>()
    val repository = mock<CreateConversationRepository>()
    val suidCalculator = mock<SuidCalculator>()
    val view = mock<CreateConversationView>()
    val controller = CreateConversationControllerImpl(service, repository, suidCalculator, view)

    @Before
    fun setUp() {
        whenever(suidCalculator.findShortestUniqueSubstring(any(), any())).thenReturn("my_public")
        whenever(service.getUsers()).thenReturn(Observable.just(listOf(User("A"), User("B"))))
        whenever(repository.getPublicKeyString()).thenReturn("my_public_key")
    }

    @Test
    fun shouldGetAllUsersOnCreate() {
        controller.onCreate()

        verify(service).getUsers()
    }

    @Test
    fun shouldGetOwnerKeyFromRepositoryOnCreate() {
        controller.onCreate()

        verify(repository).getPublicKeyString()
    }

    @Test
    fun shouldShowShortestUniqueSubstringOfUserRSA() {
        controller.onCreate()

        verify(view).showShortestUniqueId(Mockito.anyString())
    }

    @Test
    fun shouldShowErrorWhenRetrieveUsersFails() {
        whenever(service.getUsers()).thenReturn(Observable.error(RuntimeException()))

        controller.onCreate()

        verify(view).showError()
    }
}

