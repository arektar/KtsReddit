package com.example.ktsreddit.data.common.mappers

interface Mapper<From, To> {
    fun map(item: From): To
}
