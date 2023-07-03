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
import androidx.navigation.NavController
import com.example.ktsreddit.R
import com.example.ktsreddit.data.auth.models.AuthDefault
import com.example.ktsreddit.data.auth.models.AuthIntent
import com.example.ktsreddit.data.auth.models.AuthSuccess
import com.example.ktsreddit.data.auth.models.AuthToast
import com.example.ktsreddit.presentation.common.compose.toast
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = koinViewModel(),
) {


    val authState by viewModel.authState.collectAsState()

    val context = LocalContext.current

    val getAuthResponse =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@rememberLauncherForActivityResult
            viewModel.handleAuthResponseIntent(
                dataIntent
            )
        }

    AuthView(
        viewModel::openLoginPage,
        authState,
    )



    fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }



    LaunchedEffect(key1 = Unit) {

        viewModel.navEvents.collect { event ->
            navController.navigate(event.stringRoute)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.authEvents.collect { event ->
            when (event) {
                is AuthToast -> {
                    toast(event.toast, context)
                }
                is AuthIntent -> {
                    openAuthPage(event.intent)
                }
                is AuthSuccess -> viewModel.navigateNext()
                is AuthDefault -> {}
            }
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