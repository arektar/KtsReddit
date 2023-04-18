package com.example.ktsreddit.presentation.ui.models

import android.annotation.SuppressLint
import android.util.Patterns
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.ComplexElem
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.Item
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.SimpleElem
import kotlinx.coroutines.flow.*
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

    val mainPagingList: Flow<PagingData<Item>> = pagingList().cachedIn(viewModelScope)

    var mainListId = 0

    init {
        viewModelScope.launch {
            _authState.value =
                savedStateHandle.getStateFlow("auth", DEFAULT_AUTH_STATE).value

            _mainListState.value = generateDefaultList(100)
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

    @SuppressLint("CheckResult")
    fun toggleMainListLike(changingItem: ComplexElem) {
        /*
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



        val liked = mainPagingList.map { pagingData ->
            println("PaintingData!!!")
            println(pagingData)
            pagingData.filter { item ->
                item.id == changingItem.id
            }.map { one ->
                    if (one is ComplexElem) {
                        println("One!!!")
                        println(one)
                        one.liked = !one.liked
                    } else {
                        error("Not ComplexElem found error. Not Click not from ComplexElem.")
                    }
                }
        }


        mainPagingList.combine(liked) { list, likedElements ->  list.map {
            if (it is ComplexElem)
                it.copy(liked = true) }  }

                */



        mainPagingList.map { pagingData ->
            pagingData.map { item ->
                if (item is ComplexElem){
                    if (item.id == changingItem.id){
                        item.liked=!item.liked
                    }

                }
            }
        }.cachedIn(viewModelScope)
        println(mainPagingList)

    }

    fun generateDefaultList(count: Int): List<Item> {
        val newList = List(count) {
            mainListId += 1
            when ((1..2).random()) {
                1 -> SimpleElem(mainListId, "TestSimple", "TestSimple text")
                2 -> ComplexElem(mainListId, "TestComplex", "TestComplex text", "Me", false)
                else -> error("Wrong random number")
            }
        }
        _mainListState.value = _mainListState.value.union(newList).toList()
        return newList
    }

    fun getPage(pageNum: Int, size: Int = MAIN_LIST_PAGE_SIZE): List<Item> {
        val start: Int = pageNum * size
        val end = start + size
        return mainListState.value.subList(start, end)
    }


    fun pagingList() = Pager(
        config = PagingConfig(
            pageSize = MAIN_LIST_PAGE_SIZE,
        ),
        pagingSourceFactory = {
            MyPagingSource(::getPage)
        }
    ).flow

    //fun getMpListPaged(): Flow<PagingData<Item>> = pagingList().cachedIn(viewModelScope)

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
    val getPage: (Int) -> List<Item>
) : PagingSource<Int, Item>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val pageNumber = params.key ?: 0
            val response = getPage(pageNumber)
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




