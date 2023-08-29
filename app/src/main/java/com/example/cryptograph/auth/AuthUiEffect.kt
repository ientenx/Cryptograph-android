package com.example.cryptograph.auth

sealed interface AuthUiEffect{
    data object AuthenticationSucceed: AuthUiEffect
    data object AuthenticationFailed: AuthUiEffect
}