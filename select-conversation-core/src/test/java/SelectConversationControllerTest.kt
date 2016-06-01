import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.safechat.conversation.select.SelectConversationController
import com.safechat.conversation.select.UsersService
import org.junit.Test

class SelectConversationControllerTest {

    val service = mock<UsersService>()

    @Test
    fun shouldGetAllUsersOnCreate() {
        val controller = SelectConversationController(service)
        controller.onCreate()
        verify(service).getUsers()
    }
}