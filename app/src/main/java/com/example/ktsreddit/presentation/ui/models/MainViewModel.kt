package com.example.ktsreddit.presentation.ui.models

import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.ComplexElem
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.Item
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.SimpleElem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    //private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _authState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _mainListState = MutableStateFlow(DEFAULT_MAIN_LIST_STATE)
    val mainListState: StateFlow<List<Item>> = _mainListState.asStateFlow()

    init {
        viewModelScope.launch {
            _authState.value =
                savedStateHandle.getStateFlow("auth", DEFAULT_AUTH_STATE).value

            _mainListState.value = generateDefaultList()
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

    fun generateDefaultList():List<Item>{
        return List(20){
            when ((1..2).random()){
                1 -> SimpleElem(it,"TestSimple")
                2 -> ComplexElem(it,"TestSimple","Me",false)
                else -> error("Wrong random number")
            }
        }
    }


    companion object {
        val DEFAULT_AUTH_STATE = AuthState("", "")
        val DEFAULT_MAIN_LIST_STATE = emptyList<Item>()
    }
}

data class AuthState(
    val login: String,
    val password: String,
    val loginIsCorrect: Boolean = false,
    val passwordIsCorrect: Boolean = false,
) {
    val loginEnabled = this.loginIsCorrect && this.passwordIsCorrect
}




