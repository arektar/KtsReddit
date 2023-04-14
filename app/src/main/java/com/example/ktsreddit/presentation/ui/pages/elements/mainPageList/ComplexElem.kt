package com.example.ktsreddit.presentation.ui.pages.elements.mainPageList

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

data class ComplexElem (
    override val id:Int,
    val title: String,
    val author: String,
    val liked: Boolean,
):Item()


@Composable
fun complexMpItem(elem:ComplexElem) {

}

@Preview(showBackground = true)
@Composable
fun complexMpItemPreview() {
    val defaultElem = ComplexElem(0,"Test","Test",false)
    KtsRedditTheme {
        complexMpItem(
            elem=defaultElem
        )
    }
}