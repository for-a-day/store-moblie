package com.nagane.table.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// 테이블 오더 엔티티
@Entity(tableName = "StoreTable")
data class StoreTableEntity (
    // 테이블 코드(pk)
    @ColumnInfo(name = "tableCode")
    @PrimaryKey
    var tableCode : String,
    // 가게 코드(fk, but 테이블 오더에서 가게 정보 저장 x, unique)
    @ColumnInfo(name="storeCode")
    var storeCode : String,
    // 현재 테이블 번호
    @ColumnInfo(name="tableNumber")
    val tableNumber: Number,
    // 현재 테이블 이름
    @ColumnInfo(name="tableName")
    val tableName : String,
)