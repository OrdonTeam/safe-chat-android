package com.safechat.encryption

import com.safechat.conversation.ConversationCipher
import com.safechat.encryption.base.Base64Encoder
import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.symmetric.AESCipher
import rx.Observable
import javax.crypto.SecretKey

class ConversationCipherImpl : ConversationCipher {

    override fun decryptMessages(symmetricKey: String, encryptedMessages: List<com.safechat.message.Message>): Observable<List<com.safechat.message.Message>> {
        val secretKey = KeysBase64Cipher.decodeSymmetricKey(symmetricKey)
        return Observable.from(encryptedMessages).flatMap { decrypt(it, secretKey) }.toList()
    }

    private fun decrypt(message: com.safechat.message.Message, secretKey: SecretKey): Observable<com.safechat.message.Message> {
        return AESCipher.decrypt(Base64Encoder.decode(message.text), secretKey)
                .map { message.copy(text = String(it)) }
    }

    override fun encryptMessage(symmetricKey: String, message: com.safechat.message.Message): Observable<com.safechat.message.Message> {
        val secretKey = KeysBase64Cipher.decodeSymmetricKey(symmetricKey)
        return encrypt(message, secretKey)
    }

    private fun encrypt(message: com.safechat.message.Message, secretKey: SecretKey): Observable<com.safechat.message.Message> {
        return AESCipher.encrypt(message.text.toByteArray(), secretKey)
                .map { message.copy(text = String(Base64Encoder.encode(it))) }
    }
}
