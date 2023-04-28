package com.example.ktsreddit.presentation.common.compose.mainlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktsreddit.presentation.common.compose.mainlist.components.MainListCard
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.compose_theme.myColors
import com.example.ktsreddit.presentation.common.items.SimpleElem


@Composable
fun SimpleMpItemView(elem: SimpleElem) {

    val textsMod = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    val columnMod = Modifier.padding(5.dp)

    MainListCard() {
        Column(modifier = columnMod) {
            Text(text = elem.title, modifier = textsMod)
            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)
            Text(text = elem.text, modifier = textsMod)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SimpleMpItemPreview() {
    val defaultElem = SimpleElem(0, "Test", "Test text")
    KtsRedditTheme {
        SimpleMpItemView(
            defaultElem
        )
    }
}