package com.example.ktsreddit.common.compose.mainlist.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ktsreddit.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.common.compose_theme.myColors


@Composable
fun MainListCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 10.dp,
        border = BorderStroke(1.dp, MaterialTheme.myColors.cardMpBorder),
        content = content
    )
}


@Preview(showBackground = true)
@Composable
fun MainListCardPreview() {
    KtsRedditTheme {
        MainListCard {}
    }
}