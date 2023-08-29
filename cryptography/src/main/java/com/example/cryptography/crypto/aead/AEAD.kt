package com.example.cryptography.crypto.aead

import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle

open class AEAD(keysetHandle: KeysetHandle): IAeadDecryptor, IAeadEncryptor {

    val aead = keysetHandle.getPrimitive(Aead::class.java)

    override fun decrypt(cipherData: ByteArray, aData: ByteArray): String = String(aead.decrypt(cipherData, aData))

    override fun encrypt(plainData: ByteArray, aData: ByteArray): ByteArray = aead.encrypt(plainData, aData)

}