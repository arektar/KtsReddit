package com.example.ktsreddit.presentation.ui.pages


import BaseComposeFragment
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager


class OnBoardingFragment : BaseComposeFragment() {


    private lateinit var navController: NavController

    @Composable
    override fun ComposeScreen() {
        //OnBoarding(::navigateNext)
        OnBoardingPager(navigateNext = ::navigateNext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun navigateNext() {
        navController.navigate(
            OnBoardingFragmentDirections.actionOnBoardingFragmentToAuthorisationFragment()
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(pageCount: Int = 3, navigateNext: () -> Unit) {
    HorizontalPager(count = pageCount) { page ->
        // Our page content
        when (page) {
            0 -> FirstOnBoarding()
            1 -> SecondOnBoarding()
            2 -> ThirdOnBoarding(navigateNext)
            else -> error("Bad page num")
        }
    }
}

@Composable
fun FirstOnBoarding() {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(R.string.start_screen_hello)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(R.string.start_screen_hello)
            }
        }
    }
}

@Composable
fun SecondOnBoarding() {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(R.string.start_screen_welcome)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(R.string.start_screen_welcome)
            }
        }
    }
}

@Composable
fun ThirdOnBoarding(navigateNext: () -> Unit) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(R.string.start_screen_clicknext)
                ToMainScreenButton(navigateNext)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(R.string.start_screen_clicknext)
                ToMainScreenButton(navigateNext)
            }
        }
    }
}
//R.string.start_screen_hello

@Composable
fun Content(string_id:Int) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = string_id), fontSize = 25.sp)
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

        OnBoardingPager {}
    }
}