package com.example.ktsreddit.presentation.ui.pages

import BaseComposeFragment
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R


class MainPageFragment : BaseComposeFragment() {


    private lateinit var navController: NavController

    @Composable
    override fun ComposeScreen() {
        MainPage(::navigateNext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun navigateNext() {
        //navController.navigate(OnboardingFragmentDirections.actionOnboardingFragmentToAuthorisationFragment())
    }
}

@Composable
fun MainPage(navigateNext: () -> Unit) {
    Image(
        imageVector = ImageVector.vectorResource(id = R.drawable.img_onboarding_lotus),
        contentDescription = "Lotus"
    )
}