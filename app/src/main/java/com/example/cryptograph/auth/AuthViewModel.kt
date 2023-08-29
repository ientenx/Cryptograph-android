package com.example.cryptograph.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptograph.model.AuthenticationResponse
import com.example.cryptograph.session.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    private val _uiEffect = MutableSharedFlow<AuthUiEffect>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiEffect = _uiEffect.asSharedFlow()

    fun register(username: String, password: String) {
        authentication {
            repository.register(username = username, password = password)
        }
    }

    fun login(username: String, password: String) {
        authentication {
            repository.login(username = username, password = password)
        }
    }

    private fun authentication(request: suspend () -> Response<AuthenticationResponse>) {
        viewModelScope.launch {
            val response = request()

            val body = response.body()
            if (response.isSuccessful && body != null) {
                userSessionManager.setAccessToken(body.accessToken)
                _uiEffect.emit(AuthUiEffect.AuthenticationSucceed)
            } else
                _uiEffect.emit(AuthUiEffect.AuthenticationFailed)
        }
    }
}