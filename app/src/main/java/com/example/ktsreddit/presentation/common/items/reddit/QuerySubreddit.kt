package com.example.ktsreddit.presentation.common.items.reddit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuerySubreddit(val subreddit: String, val category: String, val limit: Int) :
    Parcelable
