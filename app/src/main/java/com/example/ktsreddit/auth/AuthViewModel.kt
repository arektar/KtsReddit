package com.example.ktsreddit.common.model

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _authState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            _authState.value =
                savedStateHandle.getStateFlow("auth", DEFAULT_AUTH_STATE).value

        }
    }

    fun validateLogin(login: String) {
        val loginIsCorrect = Patterns.EMAIL_ADDRESS.matcher(login).matches()
        val newAuthState = _authState.value.copy(
            login = login,
            loginIsCorrect = loginIsCorrect
        )
        _authState.value = newAuthState
    }

    fun validatePassword(password: String) {
        val passwordIsCorrect = password.length >= 8
        val newAuthState = _authState.value.copy(
            password = password,
            passwordIsCorrect = passwordIsCorrect
        )
        _authState.value = newAuthState
    }


    companion object {
        val DEFAULT_AUTH_STATE = AuthState()
    }

}

data class AuthState(
    val login: String = "",
    val password: String = "",
    val loginIsCorrect: Boolean = false,
    val passwordIsCorrect: Boolean = false,
) {
    // TODO: Fix at prod
    val loginEnabled = if (BuildConfig.DEBUG) {
        true
    } else {
        this.loginIsCorrect && this.passwordIsCorrect
    }
}