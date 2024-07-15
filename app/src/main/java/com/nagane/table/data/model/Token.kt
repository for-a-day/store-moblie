package com.nagane.table.data.model

data class Tokens(
    val accessToken: String,
    val refreshToken: String
)

data class TokenResponse(
    val tokens: Tokens
)