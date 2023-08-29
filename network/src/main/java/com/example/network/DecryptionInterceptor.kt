package com.example.network

import android.text.TextUtils
import co.naji.tosse.cryptography.crypto.spring.type.DecryptionType
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody

class DecryptionInterceptor(private val networkCryptography: NetworkCryptography) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // if decrypt annotation not found then return plain body
        val decryptionMethod = request.findDecryptionMethod() ?: return response

        println("=============== DECRYPTING RESPONSE ===============")

        if (response.isSuccessful) {
            val newResponse = response.newBuilder()
            var contentType = response.header("Content-Type")
            if (TextUtils.isEmpty(contentType)) contentType = "application/json"
            val encryptedBody = response.body()!!.string()
            var decryptedString: String? = null
            try {
                decryptedString = decryptBody(
                    encryptedBody = encryptedBody.replace("\"",""),
                    decryptionMethod = decryptionMethod
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            println("Encrypted Response String => $encryptedBody")
            println("Decrypted Body => $decryptedString")
            newResponse.body(ResponseBody.create(MediaType.parse(contentType), decryptedString))
            return newResponse.build()
        }
        return response
    }

    private fun decryptBody(encryptedBody: String, decryptionMethod: DecryptionType): String? =
        networkCryptography.decrypt(encryptedBody, decryptionMethod)

    private fun Request.findDecryptionMethod(): DecryptionType? =
        findDecryptionAnnotation()?.method
}