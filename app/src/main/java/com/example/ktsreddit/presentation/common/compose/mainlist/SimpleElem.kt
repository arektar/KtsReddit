package com.example.ktsreddit.presentation.common.compose.mainlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ktsreddit.presentation.common.compose.mainlist.components.DislikeButton
import com.example.ktsreddit.presentation.common.compose.mainlist.components.LikeButton
import com.example.ktsreddit.presentation.common.compose.mainlist.components.MainListCard
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.compose_theme.myColors
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.items.reddit.RedditListSimpleItem


@Composable
fun SimpleMpItemView(
    elem: RedditListSimpleItem,
    onLikeClick: (RedditItem) -> Unit,
    onDislikeClick: (RedditItem) -> Unit
) {

    val defaultTextsMod = Modifier.padding(horizontal = 10.dp, vertical = 1.dp)
    val titleTextsMod = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
    val authorTextsMod = Modifier
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .fillMaxWidth()
    val likesTextsMod = Modifier
        .padding(horizontal = 3.dp, vertical = 5.dp)


    val columnMod = Modifier.padding(5.dp)

    MainListCard() {
        Column(modifier = columnMod) {

            Text(
                text = elem.author,
                modifier = authorTextsMod,
                textAlign = TextAlign.End,
                fontStyle = FontStyle.Italic
            )

            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)
            Text(text = elem.title, modifier = titleTextsMod, fontWeight = FontWeight.Bold)
            Text(text = elem.selftext, modifier = defaultTextsMod)
            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)

            Row() {
                LikeButton(elem = elem, onClick = onLikeClick)
                Text(
                    text = elem.score.toString(),
                    modifier = likesTextsMod.align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
                DislikeButton(elem = elem, onClick = onDislikeClick)
            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun ComplexMpItemPreview() {
    val defaultElem = RedditListSimpleItem(
        "0",
        "0",
        "Test",
        "Test",
        "Test",
        "Test",
        1,
        null,
        false,
        0,
        7,
        "Test",
    )
    KtsRedditTheme {
        SimpleMpItemView(
            elem = defaultElem,
            {}, {}
        )
    }
}

