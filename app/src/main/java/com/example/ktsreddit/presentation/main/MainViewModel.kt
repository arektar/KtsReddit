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

    val mainListState: StateFlow<List<RedditItem>> =
        savedStateHandle.getStateFlow(MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)


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
        val like = when (changingItem.getLikeStatus()) {
            false -> null
            null -> true
            true -> null
        }
        val plusScore = when (changingItem.getLikeStatus()) {
            false -> 1
            null -> 1
            true -> -1
        }
        val likeVote = when (changingItem.getLikeStatus()) {
            false -> 0
            null -> 1
            true -> 0
        }
        sendPostLike(changingItem, likeVote)
        setLikeInList(changingItem, like, plusScore)

    }

    @SuppressLint("CheckResult")
    fun toggleMainListDislike(changingItem: RedditItem) {
        val like = when (changingItem.getLikeStatus()) {
            false -> null
            null -> false
            true -> null
        }
        val plusScore = when (changingItem.getLikeStatus()) {
            false -> 1
            null -> -1
            true -> -1
        }
        val likeVote = when (changingItem.getLikeStatus()) {
            false -> 0
            null -> 1
            true -> 0
        }
        sendPostLike(changingItem, likeVote)

        setLikeInList(changingItem, like, plusScore)

    }

    private fun sendPostLike(changingItem: RedditItem, like: Int) {
        viewModelScope.launch {
            val response = repository.votePost(like, changingItem.id())
            println(response)
        }
    }

    private fun setLikeInList(changingItem: RedditItem, like: Boolean?, plusScore: Int) {
        val changingList = mainListState.value.toMutableList()

        val elem = changingList.first { it.id == changingItem.id }
        val index = changingList.indexOf(elem)
        val new = elem.setLikeStatus(like)
        new.plusScore(plusScore)

        changingList[index] = new
        println(changingList)

        savedStateHandle[MAIN_LIST_SUBREDDIT_KEY] = changingList.toList()
    }


    companion object {
        val DEFAULT_MAIN_LIST_STATE = mutableListOf<RedditItem>()

        private const val QUERY_SUBREDDIT = "QUERY_SUBREDDIT"
        private const val MAIN_LIST_SUBREDDIT_KEY = "MAIN_LIST_SUBREDDIT"
        val DEFAULT_REDDIT_QUERY = QuerySubreddit("Popular", "top", "20")

    }
}


