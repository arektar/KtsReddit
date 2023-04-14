package com.example.ktsreddit.presentation.ui.models

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    //private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _uiState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value =
                savedStateHandle.getStateFlow("auth", DEFAULT_AUTH_STATE).value
        }
    }


    fun validateLogin(login: String) {
        val loginIsCorrect = Patterns.EMAIL_ADDRESS.matcher(login).matches()
        val newAuthState = _uiState.value.copy(
            login = login,
            loginIsCorrect = loginIsCorrect
        )
        _uiState.value = newAuthState
    }

    fun validatePassword(password: String) {
        val passwordIsCorrect = password.length >= 8
        val newAuthState = _uiState.value.copy(
            password = password,
            passwordIsCorrect = passwordIsCorrect
        )
        _uiState.value = newAuthState
    }


    companion object {
        val DEFAULT_AUTH_STATE = AuthState("", "")
    }
}

data class AuthState(
    val login: String,
    val password: String,
    val loginIsCorrect: Boolean = false,
    val passwordIsCorrect: Boolean = false,
){
    val loginEnabled = this.loginIsCorrect && this.passwordIsCorrect
}




