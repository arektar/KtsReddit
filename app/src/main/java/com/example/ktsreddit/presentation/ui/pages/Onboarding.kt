package com.example.ktsreddit.presentation.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme


@Composable
fun Onboarding() {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = stringResource(id = R.string.start_screen_hello),  fontSize = 25.sp)
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable._101381_light),
            contentDescription = "Lotus"
        )

        Button(onClick = { /*TODO*/ }) {
            Text(stringResource(id = R.string.start_screen_next), fontSize = 25.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KtsRedditTheme {
        Onboarding()
    }
}