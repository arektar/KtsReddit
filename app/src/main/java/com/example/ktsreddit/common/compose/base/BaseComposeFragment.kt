package com.example.ktsreddit.common.compose.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import com.example.ktsreddit.common.compose_theme.KtsRedditTheme

abstract class BaseComposeFragment : Fragment() {

    val localNavController: NavHostController
        get() = _localNavController ?: error("No localNavController")

    private var _localNavController: NavHostController? = null

    @Composable
    @Suppress("TopLevelComposableFunctions")
    protected abstract fun ComposeScreen()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            KtsRedditTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background,
                ) {
                    ComposeScreen()
                }
            }
        }
    }
}