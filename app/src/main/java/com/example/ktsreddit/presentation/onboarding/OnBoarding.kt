package com.example.ktsreddit.presentation.onboarding


import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager


@Composable
fun OnBoardingScreen(
    navController: NavController,
    viewModel: OnBoardingViewModel = viewModel(),
) {
    OnBoardingPager(navigateNext = viewModel::onNextClick)


    LaunchedEffect(key1 = Unit) {
        viewModel.navEvents.collect { event ->
            event.navigate(navController)
        }
    }
}



/*class OnBoardingFragment : BaseComposeFragment() {


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
}*/


@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(pageCount: Int = 3, navigateNext: () -> Unit) {
    HorizontalPager(count = pageCount) { page ->
        // Our page content
        when (page) {
            0 -> OnBoardingPage(R.string.start_screen_hello)
            1 -> OnBoardingPage(R.string.start_screen_welcome)
            2 -> FinalOnBoardingPage(R.string.start_screen_clicknext, navigateNext)
            else -> error("Bad page num")
        }
    }
}

@Composable
fun OnBoardingPage(@StringRes stringRes: Int) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(stringRes)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(stringRes)
            }
        }
    }
}

@Composable
fun FinalOnBoardingPage(@StringRes stringRes: Int, navigateNext: () -> Unit) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(stringRes)
                ToMainScreenButton(navigateNext)
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Content(stringRes)
                ToMainScreenButton(navigateNext)
            }
        }
    }
}
//R.string.start_screen_hello

@Composable
fun Content(@StringRes stringRes: Int) {
    val defaultTextsMod = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = stringRes),
            fontSize = 25.sp,
            modifier = defaultTextsMod,
            textAlign = TextAlign.Center
        )
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