package com.swallow.cracker.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuerySubreddit(var subreddit: String, var category: String, var limit: String) :
    Parcelable
