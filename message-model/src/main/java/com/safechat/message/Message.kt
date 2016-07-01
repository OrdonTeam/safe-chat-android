package com.safechat.message

data class Message(val text: String, val isYours: Boolean, val isRead: Boolean, val timestamp: Long)