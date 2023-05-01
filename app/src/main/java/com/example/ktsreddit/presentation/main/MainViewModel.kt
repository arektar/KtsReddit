package com.example.ktsreddit.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.data.RedditRepository

import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.items.reddit.QuerySubreddit
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {


    //private val _mainListState: SaveableMutableSaveStateFlow<MutableList<RedditItem>> = SaveableMutableSaveStateFlow(savedStateHandle,MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)

    //val mainListState: SaveableMutableSaveStateFlow<MutableList<RedditItem>> = SaveableMutableSaveStateFlow(savedStateHandle,MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)
    //savedStateHandle.getStateFlow(MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)
    //val mainListState: StateFlow<List<RedditItem>> = _mainListState.asStateFlow()
    val mainListState: StateFlow<List<RedditItem>> =
        savedStateHandle.getStateFlow(MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)

    //val mainListState:MutableStateFlow

    //val mainListState = _mainListState.asStateFlow()


    val queryFlow: StateFlow<QuerySubreddit> =
        savedStateHandle.getStateFlow(QUERY_SUBREDDIT, DEFAULT_REDDIT_QUERY)


    private val repository = RedditRepository()


    init {
        viewModelScope.launch {

            queryFlow.map {
                repository.simpleGetSubreddit(it.subreddit, it.category, it.limit)
            }.onEach {
                savedStateHandle[MAIN_LIST_SUBREDDIT_KEY] = it
            }.collect()

            repository.getModHash()
        }
    }

    fun searchPosts() {
        savedStateHandle[QUERY_SUBREDDIT] = DEFAULT_REDDIT_QUERY
    }


    @SuppressLint("CheckResult")
    fun toggleMainListLike(changingItem: RedditItem) {
        sendPostLike(changingItem)
        setLikeInList(changingItem)

    }

    private fun sendPostLike(changingItem: RedditItem) {
        val like = if (changingItem.getLikeStatus() != true) 1 else 0
        viewModelScope.launch {
            val response = repository.votePost(like, changingItem.id())
            println(response)
        }
    }

    private fun setLikeInList(changingItem: RedditItem) {
        val changingList = mainListState.value.toMutableList()

        val elem = changingList.first { it.id == changingItem.id }
        val index = changingList.indexOf(elem)
        val status = elem.getLikeStatus() != true
        val new = elem.setLikeStatus(status)

        //val newList = mainListState.value.set(index, new)

        changingList[index] = new
        println(changingList)

        //_mainListState.value = changingList
        savedStateHandle[MAIN_LIST_SUBREDDIT_KEY] = changingList.toList()
    }


    companion object {
        val DEFAULT_MAIN_LIST_STATE = mutableListOf<RedditItem>()

        val DEFAULT_POSTS_FLOW = emptyList<RedditItem>()
        private const val QUERY_SUBREDDIT = "QUERY_SUBREDDIT"
        private const val MAIN_LIST_SUBREDDIT_KEY = "MAIN_LIST_SUBREDDIT"
        val DEFAULT_REDDIT_QUERY = QuerySubreddit("Popular", "top", "20")

    }
}


