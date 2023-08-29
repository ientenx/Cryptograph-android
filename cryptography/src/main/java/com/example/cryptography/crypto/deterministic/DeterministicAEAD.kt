package com.example.cryptography.crypto.deterministic

import com.google.crypto.tink.DeterministicAead
import com.google.crypto.tink.KeysetHandle

open class DeterministicAEAD(keysetHandle: KeysetHandle): IDeterministicAeadDecryptor,
    IDeterministicAeadEncryptor {

    val daead = keysetHandle.getPrimitive(DeterministicAead::class.java)


    override fun decrypt(cipherData: ByteArray, aData: ByteArray): String =
        String(daead.decryptDeterministically(cipherData, aData))

    override fun encrypt(plainData: ByteArray, aData: ByteArray): ByteArray =
        daead.encryptDeterministically(plainData, aData)

}