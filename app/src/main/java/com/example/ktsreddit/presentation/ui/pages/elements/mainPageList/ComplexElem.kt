package com.example.ktsreddit.presentation.ui.pages.elements.mainPageList

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

data class ComplexElem(
    override val id: Int,
    val title: String,
    val text: String,
    val author: String,
    val liked: Boolean,
) : Item()


@Composable
fun ComplexMpItemView(elem: ComplexElem, onLikeClick: (ComplexElem) -> Unit) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = elem.title, modifier = textsMod)
                Text(text = elem.author, modifier = textsMod)
            }
            Divider(color = Color(R.color.cardMpBorder), thickness = 1.dp)
            Text(text = elem.text, modifier = textsMod)
            Divider(color = Color(R.color.cardMpBorder), thickness = 1.dp)
            IconButton(onClick = { onLikeClick(elem) }) {
                if (elem.liked) Image(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_up_active_24),
                    contentDescription = "",
                )
                else
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_up_passive_24),
                        contentDescription = "",
                    )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun complexMpItemPreview() {
    val defaultElem = ComplexElem(0, "Test", "Test", "Test", false)
    KtsRedditTheme {
        ComplexMpItemView(
            elem = defaultElem,
            {}
        )
    }
}