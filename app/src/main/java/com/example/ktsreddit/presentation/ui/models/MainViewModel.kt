package com.example.ktsreddit.presentation.ui.models

import android.util.Patterns
import android.widget.Toast
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

    fun toggleMainListLike(item:ComplexElem){
        val newList = _mainListState.value.toMutableList().apply {
            val one = first {
                it.id == item.id
            }
            val new:ComplexElem
            val ind = indexOf(one)
            if (one is ComplexElem) {
                new = one.copy (
                    liked = !item.liked
                )
            } else {
                error("Not ComplexElem found error. Not Click not from ComplexElem.")
            }
            set(ind,new)
        }.toList()
        _mainListState.value = newList

    }

    fun generateDefaultList():List<Item>{
        val id = 0
        return List(20){
            when ((1..2).random()){
                1 -> SimpleElem(it,"TestSimple","TestSimple text")
                2 -> ComplexElem(it,"TestComplex","TestComplex text","Me",false)
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
    val loginEnabled = true //this.loginIsCorrect && this.passwordIsCorrect
}




