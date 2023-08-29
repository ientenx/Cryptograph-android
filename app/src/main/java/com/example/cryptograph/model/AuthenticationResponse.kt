package com.example.cryptograph.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AuthenticationResponse(
    @SerializedName("accessToken")
    val accessToken: String
)