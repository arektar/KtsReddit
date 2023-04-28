package com.example.ktsreddit.presentation.common.compose.mainlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.common.compose.mainlist.components.MainListCard
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.compose_theme.myColors
import com.example.ktsreddit.presentation.common.items.ComplexElem


@Composable
fun ComplexMpItemView(elem: ComplexElem, onLikeClick: (ComplexElem) -> Unit) {

    val textsMod = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    val columnMod = Modifier.padding(5.dp)

    MainListCard() {
        Column(modifier = columnMod) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = elem.title, modifier = textsMod)
                Text(text = elem.author, modifier = textsMod)
            }
            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)
            Text(text = elem.text, modifier = textsMod)
            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)
            IconButton(onClick = { onLikeClick(elem) }) {
                if (elem.liked) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_up_active_24),
                        contentDescription = "",
                    )
                } else {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_up_passive_24),
                        contentDescription = "",
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ComplexMpItemPreview() {
    val defaultElem = ComplexElem(0, "Test", "Test", "Test", false)
    KtsRedditTheme {
        ComplexMpItemView(
            elem = defaultElem,
            {}
        )
    }
}