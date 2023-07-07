package com.example.ktsreddit.presentation.common.mappers

interface Mapper<From, To> {
    fun map(item: From): To
}
