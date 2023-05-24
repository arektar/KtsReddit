package com.example.ktsreddit.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.data.RedditRepository
import com.example.ktsreddit.data.network.NetworkStatusTracker
import com.example.ktsreddit.presentation.common.items.reddit.LikeState
import com.example.ktsreddit.presentation.common.items.reddit.QuerySubreddit
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.navigation.NavigateEvent
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val mainListState: StateFlow<List<RedditItem>> =
        savedStateHandle.getStateFlow(MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)

    private val mutableNavEvent = OneTimeEvent<NavigateEvent>()
    val navEvents: Flow<NavigateEvent>
        get() = mutableNavEvent.receiveAsFlow()


    private val queryFlow: StateFlow<QuerySubreddit> =
        savedStateHandle.getStateFlow(QUERY_SUBREDDIT, DEFAULT_REDDIT_QUERY)


    private val repository = RedditRepository()


    val networkFlow: StateFlow<Boolean>
        get() = NetworkStatusTracker.networkFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = NetworkStatusTracker.getCurrentStatus()
        )


    init {
        initPostsProcess()

    }

    private fun initPostsProcess() {
        viewModelScope.launch {

            queryFlow.map {
                repository.simpleGetSubreddit(it.subreddit, it.category, it.limit)
            }.onEach {
                savedStateHandle[MAIN_LIST_SUBREDDIT_KEY] = it
            }.collect()

        }
    }


    fun searchPosts() {
        savedStateHandle[QUERY_SUBREDDIT] = DEFAULT_REDDIT_QUERY
    }


    @SuppressLint("CheckResult")
    fun toggleMainListLike(changingItem: RedditItem) {
        val newLikeState: LikeState = changingItem.getLikeStatus().liked()
        sendPostLike(changingItem, newLikeState.likeVote)
        setLikeInList(changingItem, newLikeState)
    }

    @SuppressLint("CheckResult")
    fun toggleMainListDislike(changingItem: RedditItem) {
        val newLikeState: LikeState = changingItem.getLikeStatus().disliked()
        sendPostLike(changingItem, newLikeState.likeVote)
        setLikeInList(changingItem, newLikeState)

    }

    private fun sendPostLike(changingItem: RedditItem, like: Int) {
        viewModelScope.launch {
            repository.votePost(like, changingItem.id())
        }
    }

    private fun setLikeInList(changingItem: RedditItem, newLikeState: LikeState) {
        val changingList = mainListState.value.toMutableList()

        val elem = changingList.first { it.id == changingItem.id }
        val index = changingList.indexOf(elem)
        val new = elem.likeUpdateScore(newLikeState)

        changingList[index] = new

        savedStateHandle[MAIN_LIST_SUBREDDIT_KEY] = changingList.toList()
    }


    companion object {
        val DEFAULT_MAIN_LIST_STATE = mutableListOf<RedditItem>()

        private const val QUERY_SUBREDDIT = "QUERY_SUBREDDIT"
        private const val MAIN_LIST_SUBREDDIT_KEY = "MAIN_LIST_SUBREDDIT"
        val DEFAULT_REDDIT_QUERY = QuerySubreddit("Popular", "top", 20)

    }
}
