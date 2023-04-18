package com.example.ktsreddit.presentation.ui.pages

import BaseComposeFragment
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.models.MainViewModel
import com.example.ktsreddit.presentation.ui.pages.elements.mainPageList.*
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme


class MainPageFragment : BaseComposeFragment() {


    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    @Composable
    override fun ComposeScreen() {
        //val mainListState by viewModel.mainListState.collectAsState()
        val mainPageListPaged = viewModel.mainPagingList.collectAsLazyPagingItems()
        MainPage(mainPageListPaged,viewModel::toggleMainListLike)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }
}

@Composable
fun MainPage(mainPageListPaged: LazyPagingItems<Item>, onLikeClick: (ComplexElem)->Unit) {

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
                key = { it.id },
            ){
                when (it) {
                    is SimpleElem -> {SimpleMpItemView(it)}
                    is ComplexElem -> {ComplexMpItemView(it,onLikeClick)}
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    KtsRedditTheme {
        //val mainListState = emptyList<Item>()
        //MainPage(
        //    mainListState
        //) { }
    }
}