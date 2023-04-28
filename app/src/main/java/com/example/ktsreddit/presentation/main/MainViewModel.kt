package com.example.ktsreddit.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.ktsreddit.presentation.common.items.ComplexElem
import com.example.ktsreddit.presentation.common.items.Item
import com.example.ktsreddit.presentation.common.items.SimpleElem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    //private val newsRepository: NewsRepository,
) : ViewModel() {


    private val _mainListState = MutableStateFlow(DEFAULT_MAIN_LIST_STATE)
    val mainListState: StateFlow<List<Item>> = _mainListState.asStateFlow()

    init {
        viewModelScope.launch {
            _mainListState.value = generateDefaultList(100)
        }
    }


    @SuppressLint("CheckResult")
    fun toggleMainListLike(changingItem: ComplexElem) {
        val newList = _mainListState.value.toMutableList().apply {
            val one = first {
                it.id == changingItem.id
            }
            val new: ComplexElem
            val ind = indexOf(one)
            if (one is ComplexElem) {
                new = one.copy(
                    liked = !changingItem.liked
                )
            } else {
                error("Not ComplexElem found error. Not Click not from ComplexElem.")
            }
            set(ind, new)
        }.toList()
        _mainListState.value = newList


    }

    private fun generateDefaultList(count: Int): List<Item> {
        val newList = List(count) {
            when ((1..2).random()) {
                1 -> SimpleElem(it, "TestSimple", "TestSimple text")
                2 -> ComplexElem(it, "TestComplex", "TestComplex text", "Me", false)
                else -> error("Wrong random number")
            }
        }
        return newList
    }



    companion object {
        val DEFAULT_MAIN_LIST_STATE = emptyList<Item>()
    }
}


