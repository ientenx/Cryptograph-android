package com.example.cryptography.crypto.deterministic

import com.google.crypto.tink.subtle.Base64
import com.google.crypto.tink.subtle.Hex

interface IDeterministicAeadDecryptor {

    fun decrypt(cipherData: ByteArray, aData: ByteArray): String

    fun decrypt(cipherData: ByteArray, aData: String): String =
        decrypt(cipherData = cipherData, aData = aData.toByteArray())

    fun decryptHex(cipherData: String, aData: ByteArray): String =
        decrypt(cipherData = Hex.decode(cipherData), aData = aData)

    fun decryptHex(cipherData: String, aData: String): String =
        decrypt(cipherData = Hex.decode(cipherData), aData = aData.toByteArray())

    fun decryptBase64(cipherData: String, aData: ByteArray): String =
        decrypt(cipherData = Base64.decode(cipherData), aData = aData)

    fun decryptBase64(cipherData: String, aData: String): String =
        decrypt(cipherData = Base64.decode(cipherData), aData = aData.toByteArray())

}