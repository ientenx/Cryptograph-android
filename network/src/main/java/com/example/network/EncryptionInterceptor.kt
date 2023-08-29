package com.example.network

import co.naji.tosse.cryptography.crypto.spring.type.EncryptionType
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import java.io.IOException

class EncryptionInterceptor(private val networkCryptography: NetworkCryptography): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val encryptionMethod = request.findEncryptionMethod()

        // if http method is GET/DELETE or encrypted method is null, then don't encrypt request
        if (request.method() in arrayOf("GET", "DELETE") || encryptionMethod == null)
            return chain.proceed(request)

        println("=============== ENCRYPTING REQUEST ===============")

        val rawBody = request.body()
        var encryptedBody: String? = null
        val mediaType = MediaType.parse("application/json")
        try {
            val plainBody: String = bodyToString(rawBody)

            encryptedBody = encryptBody(plainBody = plainBody, encryptionMethod = encryptionMethod)
            println("Raw Body=> $plainBody")
            println("Encrypted Body=> $encryptedBody")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (encryptedBody==null)
            throw IllegalStateException("request encryption failed")

        val body = RequestBody.create(mediaType, encryptedBody)
        //build new request
        request = request.newBuilder()
            .header("Content-Type", body.contentType().toString())
            .header("Content-Length", body.contentLength().toString())
            .method(request.method(), body).build()
        return chain.proceed(request)
    }


    private fun bodyToString(request: RequestBody?): String {
        return try {
            val buffer = Buffer()
            request?.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }

    private fun encryptBody(plainBody: String, encryptionMethod: EncryptionType): String? =
        networkCryptography.encrypt(
            plainBody = plainBody,
            method = encryptionMethod
        )

    private fun Request.findEncryptionMethod(): EncryptionType? = findEncryptionAnnotation()?.method
}