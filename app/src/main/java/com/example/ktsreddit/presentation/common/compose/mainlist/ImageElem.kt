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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ktsreddit.presentation.common.compose.mainlist.components.MainListCard
import com.example.ktsreddit.presentation.common.compose.mainlist.components.VoteButton
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.compose_theme.myColors
import com.example.ktsreddit.presentation.common.items.reddit.LikeState
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.items.reddit.RedditListItemImage


@Composable
fun ImageMpItemView(
    elem: RedditListItemImage,
    onLikeClick: (RedditItem) -> Unit,
    onDislikeClick: (RedditItem) -> Unit
) {

    MainListCard {
        Column(modifier = Modifier.padding(5.dp)) {

            Text(
                text = elem.author,
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.End,
                fontStyle = FontStyle.Italic
            )

            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)
            Text(
                text = elem.title,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = elem.selftext,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 1.dp)
            )
            Divider(color = MaterialTheme.myColors.cardMpBorder, thickness = 1.dp)

            Row {
                VoteButton(
                    isActive = elem.likes.isLike == true,
                    onClick = { onLikeClick(elem) },
                )
                Text(
                    text = elem.score.toString(),
                    modifier = Modifier
                        .padding(horizontal = 3.dp, vertical = 5.dp)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp
                )
                VoteButton(
                    isActive = elem.likes.isLike == false,
                    onClick = { onDislikeClick(elem) },
                    modifier = Modifier.rotate(180f),
                    colorOn = Color.Red
                )
            }


        }

    }
}


@Preview(showBackground = true)
@Composable
fun SimpleMpItemPreview() {
    val defaultElem = RedditListItemImage(
        "Test",
        "Test",
        "Test",
        "Test",
        "Test",
        "Test",
        0,
        LikeState.NotSet,
        false,
        0,
        7,
        "Test",
        "Test",
        null,
    )
    KtsRedditTheme {
        ImageMpItemView(elem = defaultElem, {}, {})
    }
}

