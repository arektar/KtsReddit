package com.example.ktsreddit.presentation.main

import com.example.ktsreddit.presentation.common.compose.base.BaseComposeFragment
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.common.compose.mainlist.ImageMpItemView
import com.example.ktsreddit.presentation.common.compose.mainlist.SimpleMpItemView
import com.example.ktsreddit.presentation.common.compose_theme.KtsRedditTheme
import com.example.ktsreddit.presentation.common.items.ComplexElem
import com.example.ktsreddit.presentation.common.items.Item
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.main.MainViewModel.Companion.DEFAULT_POSTS_FLOW
import com.swallow.cracker.ui.model.RedditListItemImage
import com.swallow.cracker.ui.model.RedditListSimpleItem


class MainPageFragment : BaseComposeFragment() {


    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    @Composable
    override fun ComposeScreen() {
        val mainListState by viewModel.mainListState.collectAsState()
        val posts by viewModel.posts.collectAsState(DEFAULT_POSTS_FLOW)
        MainPage(posts, {})
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }
}


@Composable
fun MainPage(mainPageListPaged: List<RedditItem>, onLikeClick: (RedditItem) -> Unit) {

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
                        SimpleMpItemView(it,onLikeClick)
                    }
                    is RedditListItemImage -> {
                        ImageMpItemView(it, onLikeClick)
                    }
                }
            }

        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun MainPreview() {
    KtsRedditTheme {
        val mainListState = emptyList<Item>()
        MainPage(
            mainListState
        ) { }
    }
}

 */