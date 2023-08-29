package com.example.cryptography.crypto.hybrid

import com.google.crypto.tink.HybridDecrypt
import com.google.crypto.tink.KeysetHandle

class HybridDecryptor(privateKeysetHandle: KeysetHandle) : IHybridDecryptor {

    val hybridDecryptByPrivate = privateKeysetHandle.getPrimitive(HybridDecrypt::class.java)

    override fun decryptByPrivate(cipherData: ByteArray, contextInfo: ByteArray): String =
        String(hybridDecryptByPrivate.decrypt(cipherData, contextInfo))
}