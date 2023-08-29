package com.example.cryptograph.network

import com.example.cryptograph.session.UserSessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val userSessionManager: UserSessionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        if (userSessionManager.isLoggedIn.value == true) {
            val accessToken = userSessionManager.accessToken.value
            request = request.newBuilder()
                .addHeader(
                    AUTHORIZATION_HEADER_NAME,
                    "$AUTHORIZATION_TOKEN_PREFIX $accessToken"
                )
                .build()
        }

        return chain.proceed(request)
    }

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
        private const val AUTHORIZATION_TOKEN_PREFIX = "Bearer"
    }
}