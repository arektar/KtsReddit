package com.example.ktsreddit.presentation.ui.models

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.ComplexElem
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.Item
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.SimpleElem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    //private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _authState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _mainListState = MutableStateFlow(DEFAULT_MAIN_LIST_STATE)
    val mainListState: StateFlow<List<Item>> = _mainListState.asStateFlow()

    var mainListId = 0

    init {
        viewModelScope.launch {
            _authState.value =
                savedStateHandle.getStateFlow("auth", DEFAULT_AUTH_STATE).value

            //_mainListState.value = generateDefaultList()
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
        return List(MAIN_LIST_PAGE_SIZE){
            mainListId+=1
            when ((1..2).random()){
                1 -> SimpleElem(mainListId,"TestSimple","TestSimple text")
                2 -> ComplexElem(mainListId,"TestComplex","TestComplex text","Me",false)
                else -> error("Wrong random number")
            }
        }
    }


    fun pagingList() = Pager(
            config = PagingConfig(
                pageSize = MAIN_LIST_PAGE_SIZE,
            ),
            pagingSourceFactory = {
                MyPagingSource(::generateDefaultList)
            }
        ).flow
    fun getMpListPaged(): Flow<PagingData<Item>> = pagingList().cachedIn(viewModelScope)

    companion object {
        val DEFAULT_AUTH_STATE = AuthState("", "")
        val DEFAULT_MAIN_LIST_STATE = emptyList<Item>()
        val MAIN_LIST_PAGE_SIZE = 10
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

class MyPagingSource(
    val getPage: ()->List<Item>
) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val pageNumber = params.key ?: 0
            val response = getPage()
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null
            val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null
            LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}




