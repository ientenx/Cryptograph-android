package com.example.cryptography.crypto.hybrid

import com.google.crypto.tink.subtle.Base64
import com.google.crypto.tink.subtle.Hex

interface IHybridEncryptor {
    fun encryptByPublic(plainData: ByteArray, contextInfo: ByteArray): ByteArray

    fun encryptByPublic(plainData: String, contextInfo: ByteArray): ByteArray =
        encryptByPublic(plainData = plainData.toByteArray(), contextInfo = contextInfo)

    fun encryptByPublic(plainData: ByteArray, contextInfo: String): ByteArray =
        encryptByPublic(plainData = plainData, contextInfo = contextInfo.toByteArray())

    fun encryptByPublic(plainData: String, contextInfo: String): ByteArray =
        encryptByPublic(plainData = plainData.toByteArray(), contextInfo = contextInfo.toByteArray())

    fun encryptByPublicHex(plainData: ByteArray, contextInfo: ByteArray): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptByPublicHex(plainData: String, contextInfo: ByteArray): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encrypByPublictHex(plainData: ByteArray, contextInfo: String): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptByPublicHex(plainData: String, contextInfo: String): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptByPublicBase64(plainData: ByteArray, contextInfo: ByteArray): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptByPublicBase64(plainData: String, contextInfo: ByteArray): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptByPublicBase64(plainData: ByteArray, contextInfo: String): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptByPublicBase64(plainData: String, contextInfo: String): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))


    fun encrypt(plainData: ByteArray, contextInfo: ByteArray): ByteArray =
        encryptByPublic(plainData = plainData, contextInfo = contextInfo)

    fun encrypt(plainData: String, contextInfo: ByteArray): ByteArray =
        encryptByPublic(plainData = plainData.toByteArray(), contextInfo = contextInfo)

    fun encrypt(plainData: ByteArray, contextInfo: String): ByteArray =
        encryptByPublic(plainData = plainData, contextInfo = contextInfo.toByteArray())

    fun encrypt(plainData: String, contextInfo: String): ByteArray =
        encryptByPublic(plainData = plainData.toByteArray(), contextInfo = contextInfo.toByteArray())

    fun encryptHex(plainData: ByteArray, contextInfo: ByteArray): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptHex(plainData: String, contextInfo: ByteArray): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encrypHex(plainData: ByteArray, contextInfo: String): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptHex(plainData: String, contextInfo: String): String =
        Hex.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptBase64(plainData: ByteArray, contextInfo: ByteArray): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptBase64(plainData: String, contextInfo: ByteArray): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptBase64(plainData: ByteArray, contextInfo: String): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

    fun encryptBase64(plainData: String, contextInfo: String): String =
        Base64.encode(encryptByPublic(plainData = plainData, contextInfo = contextInfo))

}