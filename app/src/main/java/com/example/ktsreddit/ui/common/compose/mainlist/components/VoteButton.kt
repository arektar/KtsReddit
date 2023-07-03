package com.example.ktsreddit.ui.common.compose.mainlist.components

import androidx.compose.foundation.Image
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.ktsreddit.R

@Composable
fun VoteButton(
    isActive: Boolean?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colorOn: Color = Color.Yellow,
    colorOff: Color = Color.Gray
) {
    IconButton(onClick = onClick) {
        if (isActive == true) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_up_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorOn),
                modifier = modifier
            )
        } else {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_thumb_up_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(colorOff),
                modifier = modifier
            )
        }
    }
}
