package com.example.ktsreddit.presentation.ui.pages


import BaseComposeFragment
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.MainActivity
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme


class OnboardingFragment : BaseComposeFragment() {


    private lateinit var navController: NavController

    @Composable
    override fun ComposeScreen() {
        Onboarding(::navigateNext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun navigateNext() {
        navController.navigate(
            OnboardingFragmentDirections.actionOnboardingFragmentToAuthorisationFragment())
    }
}

@Composable
fun Onboarding(navigateNext: () -> Unit) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Content()
                ToMainScreenButton(navigateNext)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Content()
                ToMainScreenButton(navigateNext)
            }
        }
    }
}

@Composable
fun Content() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.start_screen_hello), fontSize = 25.sp)
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.img_onboarding_lotus),
            contentDescription = "Lotus"
        )
    }
}

@Composable
fun ToMainScreenButton(navigateNext: () -> Unit) {
    Button(onClick = { navigateNext() }) {
        Text(stringResource(id = R.string.start_screen_next), fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtsRedditTheme {

        Onboarding {}
    }
}