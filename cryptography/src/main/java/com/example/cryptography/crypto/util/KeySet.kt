package com.example.cryptography.crypto.util

import com.google.crypto.tink.CleartextKeysetHandle
import com.google.crypto.tink.JsonKeysetReader
import com.google.crypto.tink.KeysetHandle

sealed class KeySet {
    data class SymmetricKeySet(val secretKey: String) : KeySet()
    data class ECIESKeySet(val privateKey: String, val publicKey: String) : KeySet()
}

val KeySet.SymmetricKeySet.secretKeysetHandle: KeysetHandle
    get() = secretKey.toKeysetHandle()

val KeySet.ECIESKeySet.privateKeysetHandle: KeysetHandle
    get() = privateKey.toKeysetHandle()

val KeySet.ECIESKeySet.publicKeysetHandle: KeysetHandle
    get() = publicKey.toKeysetHandle()

private fun String.toKeysetHandle(): KeysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withString(this))