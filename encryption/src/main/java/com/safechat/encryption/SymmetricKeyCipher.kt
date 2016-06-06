package com.safechat.encryption

import com.safechat.conversation.symmetrickey.post.PostSymmetricKeyEncryptor
import com.safechat.conversation.symmetrickey.retrieve.RetrieveSymmetricKeyDecryptor
import rx.Observable

//TODO: add real implementation
class SymmetricKeyCipher : PostSymmetricKeyEncryptor, RetrieveSymmetricKeyDecryptor {

    override fun generateSymmetricKey() = Observable.just("new_symmetric_key")

    override fun encryptSymmetricKey(otherPublicKey: String, myPrivateKey: String, newSymmetricKey: String) = Observable.just("encrypted_symmetric_key")

    override fun decryptSymmetricKey(otherPublicKey: String, myPrivateKey: String, encryptedSymmetricKey: String) = Observable.just("decrypted_symmetric_key")
}