package com.safechat.encryption

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyEncryptor
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyDecryptor
import com.safechat.encryption.asymmetric.LongArraysRSACipher
import com.safechat.encryption.asymmetric.RSACipher
import com.safechat.encryption.base.Base64Encoder
import com.safechat.encryption.base.KeysBase64Cipher
import com.safechat.encryption.generator.NewKeysGenerator
import com.safechat.encryption.util.asObservable
import rx.Observable

class SymmetricKeyCipher : PostSymmetricKeyEncryptor, RetrieveSymmetricKeyDecryptor {

    override fun generateSymmetricKey(): Observable<String> {
        return { KeysBase64Cipher.toBase64String(NewKeysGenerator.newSymmetricKey()) }.asObservable()
    }

    override fun encryptSymmetricKey(otherPublicKey: String, myPrivateKey: String, newSymmetricKey: String): Observable<String> {
        val privateKey = KeysBase64Cipher.decodePrivateKey(myPrivateKey)
        val publicKey = KeysBase64Cipher.decodePublicKey(otherPublicKey)
        return RSACipher.encrypt(newSymmetricKey.toByteArray(), privateKey)
                .flatMap { LongArraysRSACipher.encrypt(it, publicKey) }
                .flatMap { Observable.from (it) }
                .map { String(Base64Encoder.encode(it)) }
                .toList()
                .map { it.joinToString(" ") }
    }

    override fun decryptSymmetricKey(otherPublicKey: String, myPrivateKey: String, encryptedSymmetricKey: String): Observable<String> {
        val publicKey = KeysBase64Cipher.decodePublicKey(otherPublicKey)
        val privateKey = KeysBase64Cipher.decodePrivateKey(myPrivateKey)
        return Observable.from(encryptedSymmetricKey.split(" "))
                .map { Base64Encoder.decode(it) }
                .toList()
                .flatMap { LongArraysRSACipher.decrypt(it, privateKey) }
                .flatMap { RSACipher.decrypt(it, publicKey) }
                .map { String(it) }
    }
}