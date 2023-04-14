package com.example.ktsreddit.presentation.ui.pages.elements.mainPageList

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

data class SimpleElem (
    override val id: Int,
    val title: String,
):Item()


@Composable
fun simpleMpItem(elem:SimpleElem) {

}

@Preview(showBackground = true)
@Composable
fun simpleMpItemPreview() {
    val defaultElem = SimpleElem(0,"Test")
    KtsRedditTheme {
        simpleMpItem(
            defaultElem
        )
    }
}