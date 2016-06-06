package com.safechat.firebase.users

fun findUserByRsa(otherPublicKey: String) = getUsersList().map { it.first { it.rsa == otherPublicKey } }