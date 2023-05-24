package com.example.ktsreddit.presentation.auth

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ktsreddit.R
import com.example.ktsreddit.data.auth.models.*
import com.example.ktsreddit.presentation.common.compose.toast
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import kotlinx.coroutines.flow.Flow


@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel(),
) {


    val authState by viewModel.authState.collectAsState()
    val openAuthEventsFlow =  viewModel.openAuthEventsFlow

    val context = LocalContext.current

    val getAuthResponse =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@rememberLauncherForActivityResult
            viewModel.handleAuthResponseIntent(
                dataIntent,
                viewModel::onAuthCodeFailed,
                viewModel::onAuthCodeReceived
            )
        }

    AuthView(
        viewModel::openLoginPage,
        authState,
    )



    fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    @Composable
    fun observeAuthEvents(openAuthEventsFlow: Flow<AuthEvent>, navigateNext: () -> Unit) {
        LaunchedEffect(key1 = Unit) {
            openAuthEventsFlow.collect { event ->
                when (event) {
                    is AuthToast -> {
                        toast(event.toast, context)
                    }
                    is AuthIntent -> {
                        openAuthPage(event.intent)
                    }
                    is AuthSuccess -> navigateNext()
                    is AuthDefault -> {}
                }
            }
        }
    }

    observeAuthEvents(openAuthEventsFlow, viewModel::navigateNext)

    LaunchedEffect(key1 = Unit) {

        viewModel.navEvents.collect { event ->
            navController.navigate(event.stringRoute)
        }
    }


}




@Composable
fun AuthView(
    navigateNext: () -> Unit,
    authState: UIAuthState,
) {

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        LoginButton(navigateNext, authState.loginEnabled)
    }

}


@Composable
fun LoginButton(navigateNext: () -> Unit, isActive: Boolean) {
    Button(onClick = { navigateNext() }, enabled = isActive) {
        Text(stringResource(id = R.string.reddit_login), fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    KtsRedditTheme {
        val authState = UIAuthState()
        AuthView(
            {},
            authState = authState,
        )
    }
}