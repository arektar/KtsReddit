package com.example.ktsreddit.presentation.common.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun <T> StateFlow<T>.collectAsStateWithViewLifecycle(
    lifecycleOwner: LifecycleOwner = LocalViewLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    context: CoroutineContext = EmptyCoroutineContext
): State<T> = collectAsStateWithLifecycle(
    initialValue = value,
    lifecycle = lifecycleOwner.lifecycle,
    minActiveState = minActiveState,
    context = context
)

