package com.example.cryptography.crypto.hybrid

import com.google.crypto.tink.HybridDecrypt
import com.google.crypto.tink.HybridEncrypt
import com.google.crypto.tink.KeysetHandle


open class Hybrid(privateKeysetHandle: KeysetHandle, publicKeysetHandle: KeysetHandle) : IHybridDecryptor,
    IHybridEncryptor {

    val hybridEncryptByPublic = publicKeysetHandle.getPrimitive(HybridEncrypt::class.java)
    val hybridDecryptByPrivate = privateKeysetHandle.getPrimitive(HybridDecrypt::class.java)


    override fun decryptByPrivate(cipherData: ByteArray, contextInfo: ByteArray): String =
        String(hybridDecryptByPrivate.decrypt(cipherData, contextInfo))

    override fun encryptByPublic(plainData: ByteArray, contextInfo: ByteArray): ByteArray =
        hybridEncryptByPublic.encrypt(plainData, contextInfo)


}