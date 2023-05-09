package com.example.ktsreddit.presentation.common.items

abstract class Item{
    abstract val id:Int
}

data class ComplexElem(
    override val id: Int,
    val title: String,
    val text: String,
    val author: String,
    val liked: Boolean,
) : Item()

data class SimpleElem(
    override val id: Int,
    val title: String,
    val text: String,
) : Item()