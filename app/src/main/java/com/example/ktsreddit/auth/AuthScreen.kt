package com.example.ktsreddit.auth

import com.example.ktsreddit.common.compose.base.BaseComposeFragment
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R
import com.example.ktsreddit.common.model.AuthState
import com.example.ktsreddit.common.model.AuthViewModel

import com.example.ktsreddit.common.compose_theme.KtsRedditTheme

class AuthorisationFragment : BaseComposeFragment() {

    private lateinit var navController: NavController
    private val viewModel: AuthViewModel by viewModels()

    @Composable
    override fun ComposeScreen() {
        val authState by viewModel.authState.collectAsState()
        AuthView(
            ::navigateNext,
            authState,
            validateLogin = viewModel::validateLogin,
            validatePassword = viewModel::validatePassword
        )
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
fun AuthView(
    navigateNext: () -> Unit,
    authState: AuthState,
    validateLogin: (String) -> Unit,
    validatePassword: (String) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        AuthContent(authState, validateLogin, validatePassword)
        LoginButton(navigateNext, authState.loginEnabled)
    }

}

@Composable
fun AuthContent(
    authState: AuthState,
    validateLogin: (String) -> Unit,
    validatePassword: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        LoginInput(authState, onTextUpdate = {
            validateLogin(it)
        })
        PasswordInput(authState, onTextUpdate = {
            validatePassword(it)
        })

    }
}

@Composable
fun LoginInput(authState: AuthState, onTextUpdate: (String) -> Unit) {


    val login: String = authState.login
    val loginTextError: Boolean = !authState.loginIsCorrect


    TextInput(
        text = login,
        onTextUpdate = onTextUpdate,
        label = stringResource(id = R.string.mail_input),
        isError = loginTextError,
        keyboardType = KeyboardType.Email
    )
}


@Composable
fun PasswordInput(authState: AuthState, onTextUpdate: (String) -> Unit) {

    val password: String = authState.password
    val passwordTextError: Boolean = !authState.passwordIsCorrect

    TextInput(
        text = password,
        onTextUpdate = onTextUpdate,
        label = stringResource(id = R.string.password_input),
        isError = passwordTextError,
        keyboardType = KeyboardType.Password,
        visualTransformation = PasswordVisualTransformation()
    )
}


@Composable
fun TextInput(
    label: String,
    text: String,
    isError: Boolean,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextUpdate: (String) -> Unit
) {

    TextField(
        value = text,
        onValueChange = onTextUpdate,
        label = { Text(label) },
        isError = isError,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation
    )
}

@Composable
fun LoginButton(navigateNext: () -> Unit, isActive: Boolean) {
    Button(onClick = { navigateNext() }, enabled = isActive) {
        Text(stringResource(id = R.string.login), fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    KtsRedditTheme {
        val authState = AuthState()
        AuthView({},
            authState = authState,
            validateLogin = { },
            validatePassword = { }
        )
    }
}