package com.nagane.table.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse(
    val statusCode : Int,
    val message : String
)
