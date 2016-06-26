package com.safechat.user.profile

interface UserProfileView {
    fun showShortestUniqueId(suid: String)

    fun showError()
}