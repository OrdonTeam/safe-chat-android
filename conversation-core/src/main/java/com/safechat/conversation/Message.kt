package com.safechat.conversation

data class Message(val text: String, val isYours: Boolean, val isRead: Boolean, val timestamp: Long)