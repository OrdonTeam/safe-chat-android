package com.safechat.conversation.init

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test

class InitConversationControllerImplTest {

    val view = mock<InitConversationView>()
    val repository = mock<InitConversationRepository>()
    val controller = InitConversationControllerImpl(view, repository)

    @Test
    fun shouldCallOnCompleteWhenSymmetricKeyAlreadyExchanged() {
        whenever(repository.containsSavedSymmetricKey("rsa")).thenReturn(true)
        controller.onCreate("rsa")
        verify(view).complete()
    }
}
