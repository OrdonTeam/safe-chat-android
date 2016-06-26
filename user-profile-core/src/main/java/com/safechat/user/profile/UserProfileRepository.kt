package com.safechat.user.profile

interface UserProfileRepository {
    fun getPublicKeyString(): String

}