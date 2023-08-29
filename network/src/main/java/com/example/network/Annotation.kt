package com.example.network

import co.naji.tosse.cryptography.crypto.spring.type.DecryptionType
import co.naji.tosse.cryptography.crypto.spring.type.EncryptionType
import okhttp3.Request
import retrofit2.Invocation
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@Target(FUNCTION)
@Retention(RUNTIME)
annotation class EncryptRequest(
    val method: EncryptionType
)

@Target(FUNCTION)
@Retention(RUNTIME)
annotation class DecryptResponse(
    val method: DecryptionType
)

internal fun Request.findEncryptionAnnotation(): EncryptRequest? = findAnnotation()

internal fun Request.findDecryptionAnnotation(): DecryptResponse? = findAnnotation()

private inline fun <reified T> Request.findAnnotation(): T? =
    tag(Invocation::class.java)
        ?.method()
        ?.annotations
        ?.filterIsInstance<T>()
        ?.firstOrNull()