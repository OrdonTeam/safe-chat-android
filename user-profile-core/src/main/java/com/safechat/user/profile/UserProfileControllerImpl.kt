package com.safechat.user.profile

import com.safechat.user.service.UsersService

class UserProfileControllerImpl(val service: UsersService,
                                val repository: UserProfileRepository,
                                val suidCalculator: SuidCalculator,
                                val view: UserProfileView) : UserProfileController {
    override fun onCreate() {
        val myPublicKey = repository.getPublicKeyString()
        service.getUsers()
                .map { it.filterNot { it.rsa == myPublicKey } }
                .map { suidCalculator.findShortestUniqueSubstring(myPublicKey, it.map { it.rsa }) }
                .subscribe(onSuccess, { view.showError() })
    }

    val onSuccess: (String) -> Unit = {
        view.showShortestUniqueId(it)
    }
}