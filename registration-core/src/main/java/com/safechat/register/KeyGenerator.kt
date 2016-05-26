package com.safechat.register

import java.security.KeyPair

interface KeyGenerator {
    fun generateNewKey(): KeyPair
}