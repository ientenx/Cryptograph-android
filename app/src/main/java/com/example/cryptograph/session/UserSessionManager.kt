package com.example.cryptograph.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.cryptograph.App
import com.example.cryptography.crypto.util.KeySet
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionManager @Inject constructor(
    @ApplicationContext context: Context,
    private val jwtHelper: JwtHelper
) {

    private val applicationScope = (context as App).coroutineScope

    private val preferences: SharedPreferences =
        context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    val isLoggedIn: StateFlow<Boolean?> = _accessToken.map { accessToken -> accessToken != null }
        .stateIn(scope = applicationScope, SharingStarted.Eagerly, null)

    init {
        _accessToken.value = preferences.getString(ACCESS_TOKEN, null)
    }

    fun setAccessToken(accessToken: String) {
        preferences.edit {
            putString(ACCESS_TOKEN, accessToken)
            _accessToken.value = accessToken
        }
    }

    val keySet: StateFlow<KeySet?> = _accessToken.map(jwtHelper::extractKeySet)
        .stateIn(scope = applicationScope, SharingStarted.Eagerly, null)

    companion object {
        private const val ACCESS_TOKEN = "at"
    }
}