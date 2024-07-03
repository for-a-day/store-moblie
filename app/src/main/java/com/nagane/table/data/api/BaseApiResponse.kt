package com.nagane.table.data.api

data class ApiResponse<T>(
    val statusCode: Int,
    val message: String,
    val data: T? = null
)
