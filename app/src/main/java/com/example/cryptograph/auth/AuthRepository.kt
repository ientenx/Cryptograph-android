package com.example.cryptograph.auth

import com.example.cryptograph.model.AuthenticationResponse
import com.example.cryptograph.network.AuthService
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val service: AuthService) {

    suspend fun register(username: String, password: String): Response<AuthenticationResponse> {
        return service.register(username = username, password = password)
    }

    suspend fun login(username: String, password: String): Response<AuthenticationResponse> {
        return service.login(username = username, password = password)
    }
}