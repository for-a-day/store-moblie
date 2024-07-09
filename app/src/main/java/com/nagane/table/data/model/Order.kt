package com.nagane.table.data.model

import java.time.LocalDateTime

data class OrderMenuDto(
    val menuNo: Long,
    val menuName: String,
    val quantity: Int,
)

data class OrderCreateDto(
    val amount: Int,
    val paymentMethod: String,
    val storeCode: String,
    val tableCode: String,
    val orderCase: String,
    val orderMenuList: List<OrderMenuDto>,
)

data class OrderMenuCreateList(
    val orderMenuList: List<OrderMenuDto>
)

data class OrderList(
    val orderList : List<Order>
)

data class Order(
    val orderNo: Long,
    val amount: Int,
    val orderDate: LocalDateTime,
    val state : Int,
    val paymentMethod: String,
    val updateDate : LocalDateTime,
    val tableNo: Int,
    val tableNumber: Int,
    val orderMenuList: List<OrderMenuDto>
)
