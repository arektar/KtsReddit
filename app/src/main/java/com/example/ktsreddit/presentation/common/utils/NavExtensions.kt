package com.example.ktsreddit.presentation.common.utils

import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import kotlinx.coroutines.channels.Channel

fun NavController.resetNavGraph(@NavigationRes navGraph: Int) {
    val newGraph = navInflater.inflate(navGraph)
    graph = newGraph
}

@Suppress("FunctionName")
fun <T> OneTimeEvent(): Channel<T> = Channel(Channel.BUFFERED)