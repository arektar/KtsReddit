package com.example.ktsreddit.presentation.ui.pages

import android.content.res.Configuration
import android.util.Patterns
import android.widget.EditText
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

class AuthorisationFragment : Fragment() {

}


@Composable
fun AuthView() {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                AuthContent()
                LoginButton()
            }
        }
        else -> {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                AuthContent()
                LoginButton()
            }
        }
    }
}

@Composable
fun AuthContent() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoginInput()
        PasswordInput()

    }
}

@Composable
fun LoginInput() {
    var login by remember { mutableStateOf("") }
    var loginTextError by remember { mutableStateOf(false) }
    val loginTextUpdate = { data: String ->
        login = data
        val isValid = Patterns.EMAIL_ADDRESS.matcher(login).matches()
        loginTextError = !isValid
    }


    ValidationInput(
        name = login,
        label = stringResource(id = R.string.mail_input),
        nameUpdate = loginTextUpdate,
        nameError = loginTextError,
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


    ValidationInput(
        name = password,
        label = stringResource(id = R.string.password_input),
        nameUpdate = passwordTextUpdate,
        nameError = passwordTextError,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}


@Composable
fun ValidationInput(
    name: String,
    label: String,
    nameUpdate: (String) -> Unit,
    nameError: Boolean,
    keyboardOptions: KeyboardOptions,

) {
    OutlinedTextField(
        value = name,
        onValueChange = nameUpdate,
        label = { Text(label) },
        isError = nameError,
        modifier = Modifier
            .fillMaxWidth(),
        keyboardOptions = keyboardOptions
    )
}


@Composable
fun LoginButton() {
    Button(onClick = { /*TODO*/ }) {
        Text(stringResource(id = R.string.login), fontSize = 25.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    KtsRedditTheme {
        AuthView()
    }
}