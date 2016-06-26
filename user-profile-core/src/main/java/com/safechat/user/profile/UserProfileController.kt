package com.safechat.user.profile

class UserProfileController(val service: UsersService,
                            val repository: UserProfileRepository,
                            val suidCalculator: SuidCalculator,
                            val view: UserProfileView) {
    fun onCreate() {
        val myPublicKey = repository.getPublicKeyString()
        service.getUsers()
                .map { it.filterNot { it.rsa == myPublicKey } }
                .map { suidCalculator.findShortestUniqueSubstring(myPublicKey, it.map { it.rsa }) }
                .subscribe(onSuccess, {})
    }

    val onSuccess: (String) -> Unit = {
        view.showShortestUniqueId(it)
    }
}