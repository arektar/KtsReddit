package com.example.ktsreddit.presentation.common.compose.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.utils.LocalViewLifecycleOwner

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
                    CompositionLocalProvider(LocalViewLifecycleOwner provides viewLifecycleOwner ) {
                        _localNavController = rememberNavController()
                        ComposeScreen()
                    }

                }
            }
        }
    }
}