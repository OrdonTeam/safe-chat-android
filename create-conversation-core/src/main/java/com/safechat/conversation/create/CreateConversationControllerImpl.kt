package com.safechat.conversation.create

class CreateConversationControllerImpl(val service: CreateConversationService,
                                       val repository: CreateConversationRepository,
                                       val suidCalculator: SuidCalculator,
                                       val view: CreateConversationView) : CreateConversationController {
    private var users: List<User>? = null

    override fun onCreate() {
        val myPublicKey = repository.getPublicKeyString()
        service.getUsers()
                .map { it.filterNot { it.rsa == myPublicKey } }
                .doOnNext { users = it }
                .map { suidCalculator.findShortestUniqueSubstring(myPublicKey, it.map { it.rsa }) }
                .subscribe(onSuccess, { view.showError() })
    }

    override fun onCreateConversationButtonClick(suid: String) {
        val friend = if (suid.isNotBlank()) users!!.firstOrNull { it.rsa.contains(suid) } else null
        if (friend != null) {
            view.showUserFoundScreen(friend.rsa)
        } else {
            view.showUserNotFound()
        }
    }

    private val onSuccess: (String) -> Unit = {
        view.showShortestUniqueId(it)
    }
}