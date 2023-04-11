package com.example.ktsreddit.presentation.ui.pages

import BaseComposeFragment
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.models.AuthState
import com.example.ktsreddit.presentation.ui.models.MainViewModel
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme
import kotlinx.coroutines.flow.observeOn

class AuthorisationFragment : BaseComposeFragment() {

    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    //private val viewBinding by viewBinding()

    @Composable
    override fun ComposeScreen() {
        AuthView(::navigateNext, viewModel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    private fun navigateNext() {
        navController.navigate(
            AuthorisationFragmentDirections.actionAuthorisationFragmentToMainPageFragment()
        )
    }

}


@Composable
fun AuthView(navigateNext: () -> Unit, viewModel: MainViewModel) {

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        AuthContent(viewModel)
        LoginButton(navigateNext)
    }

}

@Composable
fun AuthContent(viewModel: MainViewModel) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val authState by viewModel.uiState.collectAsState()

        LoginInput(authState, onTextUpdate = {
            viewModel.validateLogin(it)
        })
        PasswordInput()

    }
}

@Composable
fun LoginInput(authState: AuthState, onTextUpdate: (String) -> Unit) {
    //var login by remember { mutableStateOf("") }
    //var loginTextError by remember { mutableStateOf(false) }

    val login: String = authState.login
    val loginTextError: Boolean = authState.loginIsCorrect


    TextField(
        value = login,
        onValueChange = onTextUpdate,
        label = { Text(stringResource(id = R.string.mail_input)) },
        isError = loginTextError,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}


@Composable
fun PasswordInput() {
    var password by remember { mutableStateOf("") }
    var passwordTextError by remember { mutableStateOf(false) }
    val passwordTextUpdate = { data: String ->
        password = data
        val isValid = password.length >= 8
        passwordTextError = !isValid
    }

    TextField(
        value = password,
        onValueChange = passwordTextUpdate,
        label = { Text(stringResource(id = R.string.password_input)) },
        isError = passwordTextError,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation()
    )
}


@Composable
fun LoginButton(navigateNext: () -> Unit) {
    Button(onClick = { navigateNext() }) {
        Text(stringResource(id = R.string.login), fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    KtsRedditTheme {
        //AuthView {}
    }
}