package com.nagane.table.data.api

data class TableLoginRequestBody(
    val tableCode: String,
    val storeCode: String,
    val tableNumber: Int,
    val tableName: String
)
