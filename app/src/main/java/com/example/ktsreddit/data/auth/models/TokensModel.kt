package com.example.ktsreddit.data.auth.models

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)
