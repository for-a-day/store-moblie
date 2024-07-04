package com.nagane.table.data.model

/** 테이블 로그인 시, 정보 넘겨주는 용도로 사용 */
data class TableLoginDto(
    val tableCode: String,
    val storeCode: String,
    val tableNumber: Int,
    val tableName: String
)
