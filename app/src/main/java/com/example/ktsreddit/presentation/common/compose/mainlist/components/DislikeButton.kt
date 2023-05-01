package com.example.ktsreddit.presentation.common.compose.mainlist.components

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem

@Composable
fun DislikeButton(elem: RedditItem, onClick: (RedditItem) -> Unit) {

    IconButton(onClick = { onClick(elem) }) {
        if (elem.getLikeStatus() == false) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_down_active_24),
                contentDescription = "",
            )
        } else {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_down_passive_24),
                contentDescription = "",
            )
        }
    }
}