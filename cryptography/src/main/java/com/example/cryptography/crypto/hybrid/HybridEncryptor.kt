package com.example.cryptography.crypto.hybrid

import com.google.crypto.tink.HybridEncrypt
import com.google.crypto.tink.KeysetHandle

class HybridEncryptor(publicKeysetHandle: KeysetHandle) : IHybridEncryptor {

    val hybridEncryptByPublic = publicKeysetHandle.getPrimitive(HybridEncrypt::class.java)
    
    override fun encryptByPublic(plainData: ByteArray, contextInfo: ByteArray): ByteArray =
        hybridEncryptByPublic.encrypt(plainData, contextInfo)
}