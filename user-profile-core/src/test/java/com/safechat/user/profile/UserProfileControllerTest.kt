package com.safechat.user.profile

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable

class UserProfileControllerTest {

    val service = mock<UsersService>()
    val repository = mock<UserProfileRepository>()
    val controller = UserProfileController(service, repository)

    @Before
    fun setUp() {
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
}

interface UserProfileRepository {
    fun getPublicKeyString(): String

}

interface UsersService {
    fun getUsers(): Observable<List<User>>
}

class UserProfileController(val service: UsersService,
                            val repository: UserProfileRepository) {
    fun onCreate() {
        service.getUsers()
        repository.getPublicKeyString()
    }

}
