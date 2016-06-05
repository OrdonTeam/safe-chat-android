package com.safechat.conversation.symmetrickey.retrieve

import rx.Observable

interface RetrieveSymmetricKeyDecryptor {

    fun decryptSymmetricKey(otherPublicKey: String, myPrivateKey: String, encryptedSymmetricKey: String): Observable<String>
}