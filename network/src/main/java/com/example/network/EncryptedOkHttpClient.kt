package com.example.network

import okhttp3.OkHttpClient

class EncryptedOkHttpClient(private val networkCryptography: NetworkCryptography) {

    fun builder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(EncryptionInterceptor(networkCryptography))
        .addInterceptor(DecryptionInterceptor(networkCryptography))
}