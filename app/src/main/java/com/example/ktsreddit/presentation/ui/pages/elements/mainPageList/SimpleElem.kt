package com.example.ktsreddit.presentation.ui.pages.elements.mainPageList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

data class SimpleElem(
    override val id: Int,
    val title: String,
    val text: String,
) : Item()


@Composable
fun SimpleMpItemView(elem: SimpleElem) {
    val cardMod = Modifier
        .fillMaxWidth()
        .padding(10.dp)

    val textsMod = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    val columnMod = Modifier.padding(5.dp)

    Card(
        modifier = cardMod,
        elevation = 10.dp,
        border = BorderStroke(1.dp, Color(R.color.cardMpBorder)),
    ) {
        Column(modifier = columnMod) {
            Text(text = elem.title, modifier = textsMod)
            Divider(color = Color(R.color.cardMpBorder), thickness = 1.dp)
            Text(text = elem.text, modifier = textsMod)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun simpleMpItemPreview() {
    val defaultElem = SimpleElem(0, "Test", "Test text")
    KtsRedditTheme {
        SimpleMpItemView(
            defaultElem
        )
    }
}