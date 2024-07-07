package com.nagane.table.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// 장바구니 entity
    @Entity(tableName = "Cart",
    indices = [Index(value = ["menuNo"], unique = true)])
data class CartEntity(
    // 메뉴 번호(pk)
    @ColumnInfo(name = "menuNo")
    var menuNo : Long,
    // 메뉴 이름
    @ColumnInfo(name = "menuName")
    var menuName : String,
    // 단품 기준 가격
    @ColumnInfo(
        name = "price")
    var price : Int,
    // 메뉴 선택 수
    @ColumnInfo(
        name = "quantity",
        defaultValue = "1")
    var quantity : Int = 1,
)  {
    // 장바구니 번호(pk)
    @ColumnInfo(name = "cartNo")
    @PrimaryKey(autoGenerate = true)
    var cartNo: Long = 0
}

