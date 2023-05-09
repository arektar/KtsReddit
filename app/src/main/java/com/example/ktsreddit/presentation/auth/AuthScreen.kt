package com.example.ktsreddit.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R
import com.example.ktsreddit.data.auth.models.AuthDefault
import com.example.ktsreddit.data.auth.models.AuthIntent
import com.example.ktsreddit.data.auth.models.AuthSuccess
import com.example.ktsreddit.data.auth.models.AuthToast
import com.example.ktsreddit.presentation.common.compose.base.BaseComposeFragment
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.utils.launchAndCollectIn
import com.example.ktsreddit.presentation.common.utils.toast
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse

class AuthorisationFragment : BaseComposeFragment() {

    private val viewModel: AuthViewModel by viewModels()

    private val getAuthResponse =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val dataIntent = it.data ?: return@registerForActivityResult
            handleAuthResponseIntent(dataIntent)
        }

    @Composable
    override fun ComposeScreen() {
        val authState by viewModel.authState.collectAsState()
        AuthView(
            viewModel::openLoginPage,
            authState,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun navigateNext() {
        findNavController().navigate(
            AuthorisationFragmentDirections.actionAuthorisationFragmentToMainPageFragment()
        )
    }

    private fun bindViewModel() {
        viewModel.openAuthEventsFlow.launchAndCollectIn(viewLifecycleOwner) {
            when (it) {
                is AuthToast -> {
                    toast(it.toast)
                }
                is AuthIntent -> {
                    openAuthPage(it.intent)
                }
                is AuthSuccess -> navigateNext()
                is AuthDefault -> {}
            }
        }


        /*
        viewModel.openAuthPageFlow.launchAndCollectIn(viewLifecycleOwner) {
            openAuthPage(it)
        }
        viewModel.toastFlow.launchAndCollectIn(viewLifecycleOwner) {
            toast(it)
        }
        viewModel.authSuccessFlow.launchAndCollectIn(viewLifecycleOwner) {
            //findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToRepositoryListFragment())
            navigateNext()
        }
        /
         */
    }

    /*private fun updateIsLoading(isLoading: Boolean) = with(binding) {
        loginButton.isVisible = !isLoading
        loginProgress.isVisible = isLoading
    }

     */

    private fun openAuthPage(intent: Intent) {
        getAuthResponse.launch(intent)
    }

    private fun handleAuthResponseIntent(intent: Intent) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> viewModel.onAuthCodeFailed(exception)
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null ->
                viewModel.onAuthCodeReceived(tokenExchangeRequest)
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