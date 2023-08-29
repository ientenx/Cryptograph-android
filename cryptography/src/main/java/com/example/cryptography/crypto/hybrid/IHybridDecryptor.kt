package com.example.cryptography.crypto.hybrid

import com.google.crypto.tink.subtle.Base64
import com.google.crypto.tink.subtle.Hex

interface IHybridDecryptor {

    fun decryptByPrivate(cipherData: ByteArray, contextInfo: ByteArray):String
    fun decryptByPrivate(cipherData: ByteArray, contextInfo: String):String =
        decryptByPrivate(cipherData = cipherData,contextInfo = contextInfo.toByteArray())
    fun decryptByPrivateHex(cipherData: String, contextInfo: ByteArray):String =
        decryptByPrivate(cipherData = Hex.decode(cipherData), contextInfo = contextInfo)
    fun decryptByPrivateHex(cipherData: String, contextInfo: String):String =
        decryptByPrivate(cipherData = Hex.decode(cipherData), contextInfo = contextInfo.toByteArray())
    fun decryptByPrivateBase64(cipherData: String, contextInfo: ByteArray):String =
        decryptByPrivate(cipherData = Base64.decode(cipherData), contextInfo = contextInfo)
    fun decryptByPrivateBase64(cipherData: String, contextInfo: String):String =
        decryptByPrivate(cipherData = Base64.decode(cipherData), contextInfo = contextInfo.toByteArray())


    fun decrypt(cipherData: ByteArray, contextInfo: ByteArray):String =
        decryptByPrivate(cipherData = cipherData, contextInfo = contextInfo)
    fun decrypt(cipherData: ByteArray, contextInfo: String):String=
        decryptByPrivate(cipherData = cipherData, contextInfo = contextInfo)
    fun decryptHex(cipherData: String, contextInfo: ByteArray):String =
        decryptByPrivateHex(cipherData = cipherData, contextInfo = contextInfo)
    fun decryptHex(cipherData: String, contextInfo: String):String =
        decryptByPrivateHex(cipherData = cipherData, contextInfo = contextInfo)
    fun decryptBase64(cipherData: String, contextInfo: ByteArray):String =
        decryptByPrivateBase64(cipherData = cipherData, contextInfo = contextInfo)
    fun decryptBase64(cipherData: String, contextInfo: String):String =
        decryptByPrivateBase64(cipherData = cipherData, contextInfo = contextInfo)
}