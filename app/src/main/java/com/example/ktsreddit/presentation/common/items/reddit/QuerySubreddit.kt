package com.example.ktsreddit.presentation.common.items.reddit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuerySubreddit(var subreddit: String, var category: String, var limit: String) :
    Parcelable
