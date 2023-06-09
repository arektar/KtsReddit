package com.example.ktsreddit.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.data.RedditRepository
import com.example.ktsreddit.data.network.NetworkStatusTracker
import com.example.ktsreddit.presentation.common.items.reddit.LikeState
import com.example.ktsreddit.presentation.common.items.reddit.QuerySubreddit
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.navigation.NawRoute
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RedditRepository,
    private val networkStatusTracker: NetworkStatusTracker
) : ViewModel() {

    val mainListState: StateFlow<List<RedditItem>> =
        savedStateHandle.getStateFlow(MAIN_LIST_SUBREDDIT_KEY, DEFAULT_MAIN_LIST_STATE)

    private val mutableNavEvent = OneTimeEvent<NawRoute>()
    val navEvents: Flow<NawRoute>
        get() = mutableNavEvent.receiveAsFlow()


    private val queryFlow: StateFlow<QuerySubreddit> =
        savedStateHandle.getStateFlow(QUERY_SUBREDDIT, DEFAULT_REDDIT_QUERY)



    val networkFlow: StateFlow<Boolean>
        get() = networkStatusTracker.networkFlow.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = networkStatusTracker.getCurrentStatus()
        )

    val fromDbStatusFlow: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(FROM_DB_STATUS_KEY, false)


    init {
        initPostsProcess()

    }

    private fun setFromDbStatus(status: Boolean) {
        savedStateHandle[FROM_DB_STATUS_KEY] = status
    }

    private suspend fun getSubreddit(query: QuerySubreddit): List<RedditItem> {

        return repository.getPosts(query, ::setFromDbStatus)

    }

    private fun initPostsProcess() {
        viewModelScope.launch(Dispatchers.IO) {

            queryFlow.map {
                getSubreddit(it)
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
        private const val FROM_DB_STATUS_KEY = "GOT_FROM_DB"
        val DEFAULT_REDDIT_QUERY = QuerySubreddit("Popular", "top", 20)

    }
}
