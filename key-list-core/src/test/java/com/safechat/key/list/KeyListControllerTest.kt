package com.safechat.key.list

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import rx.Observable

class KeyListControllerTest {

    val view = mock(KeyListView::class.java)
    val service = mock(KeyListService::class.java)
    val controller = KeyListController(view, service)

    @Before
    fun setUp() {
        Mockito.`when`(service.keys()).thenReturn(Observable.just(emptyList()))
    }

    @Test
    fun shouldCallForListOfKeysOnStart() {
        controller.start()

        verify(service).keys()
    }

    @Test
    fun shouldDisplayLoaderWhenCallingForListOfKeys() {
        controller.start()

        verify(view).showLoader()
    }

    @Test
    fun shouldHideLoaderAfterCallForListOfKeysComplete() {
        controller.start()

        verify(view).hideLoader()
    }

    @Test
    fun shouldDisplayListOfKeysAfterCallForListOfKeysComplete() {
        controller.start()

        verify(view).showKeys(emptyList())
    }

    @Test
    fun shouldHideLoaderWhenControllerWasPausedDuringCall() {
        Mockito.`when`(service.keys()).thenReturn(Observable.never())
        controller.start()

        controller.pause()

        verify(view).hideLoader()
    }

    @Test
    fun shouldShowErrorWhenCallForListOfKeysEndsWithError() {
        Mockito.`when`(service.keys()).thenReturn(Observable.error(RuntimeException()))

        controller.start()

        verify(view).showKeysError()
    }
}

