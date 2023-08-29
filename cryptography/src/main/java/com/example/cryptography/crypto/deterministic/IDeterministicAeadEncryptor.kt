package com.example.cryptography.crypto.deterministic

import com.google.crypto.tink.subtle.Base64
import com.google.crypto.tink.subtle.Hex

interface IDeterministicAeadEncryptor {

    fun encrypt(plainData: ByteArray, aData: ByteArray): ByteArray

    fun encrypt(plainData: String, aData: ByteArray): ByteArray =
        encrypt(plainData = plainData.toByteArray(), aData = aData)

    fun encrypt(plainData: ByteArray, aData: String): ByteArray =
        encrypt(plainData = plainData, aData = aData.toByteArray())

    fun encrypt(plainData: String, aData: String): ByteArray =
        encrypt(plainData = plainData.toByteArray(), aData = aData.toByteArray())

    fun encryptHex(plainData: ByteArray, aData: ByteArray): String =
        Hex.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptHex(plainData: String, aData: ByteArray): String =
        Hex.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptHex(plainData: ByteArray, aData: String): String =
        Hex.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptHex(plainData: String, aData: String): String =
        Hex.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptBase64(plainData: ByteArray, aData: ByteArray): String =
        Base64.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptBase64(plainData: String, aData: ByteArray): String =
        Base64.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptBase64(plainData: ByteArray, aData: String): String =
        Base64.encode(encrypt(plainData = plainData, aData = aData))

    fun encryptBase64(plainData: String, aData: String): String =
        Base64.encode(encrypt(plainData = plainData, aData = aData))

}