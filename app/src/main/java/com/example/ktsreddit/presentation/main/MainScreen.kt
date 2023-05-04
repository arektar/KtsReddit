package com.example.ktsreddit.presentation.main

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.common.compose.base.BaseComposeFragment
import com.example.ktsreddit.presentation.common.compose.mainlist.ImageMpItemView
import com.example.ktsreddit.presentation.common.compose.mainlist.SimpleMpItemView
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.items.reddit.RedditListItemImage
import com.example.ktsreddit.presentation.common.items.reddit.RedditListSimpleItem


class MainPageFragment : BaseComposeFragment() {

    private val viewModel: MainViewModel by viewModels()

    @Composable
    override fun ComposeScreen() {
        val mainListState by viewModel.mainListState.collectAsState()
        MainPage(mainListState, viewModel::toggleMainListLike, viewModel::toggleMainListDislike)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}


@Composable
fun MainPage(
    mainPageListPaged: List<RedditItem>,
    onLikeClick: (RedditItem) -> Unit,
    onDislikeClick: (RedditItem) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = ImageVector.vectorResource(id = R.drawable.img_onboarding_lotus),
                contentDescription = "Lotus"
            )
        }

        LazyColumn(Modifier.fillMaxSize()) {
            items(
                items = mainPageListPaged,
                key = { item -> item.id },
                contentType = { it::class.java.name }
            ) {
                when (it) {
                    is RedditListSimpleItem -> {
                        SimpleMpItemView(it, onLikeClick, onDislikeClick)
                    }
                    is RedditListItemImage -> {
                        ImageMpItemView(it, onLikeClick, onDislikeClick)
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    KtsRedditTheme {
        val mainListState = emptyList<RedditItem>()
        MainPage(
            mainListState,
            { }, { }
        )
    }
}

