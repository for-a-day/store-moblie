package com.nagane.table.data.model

import java.time.LocalDateTime

data class OrderMenuDto(
    val menuNo: Long,
    val menuName: String,
    val quantity: Int,
)

data class OrderMenuResponseDto(
    val menuNo: Long,
    val menuName: String,
    val quantity: Int,
    val price: Int,
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
    val orderMenuDetailList: List<OrderMenuDto>
)

data class OrderList(
    val orderList : List<Order>
)

data class Order(
    val orderNo: Long,
    val amount: Int,
    val orderDate: String,
    val state : Int,
    val paymentMethod: String,
    val updatedDate : String,
    val tableNo: Int,
    val tableNumber: Int,
    val orderMenuList: List<OrderMenuDto>
)

data class OrderResponseList(
    val orderList : List<OrderResponseDto>
)

data class OrderResponseDto(
    val orderNo: Long,
    val amount: Int,
    val orderDate: String,
    val paymentMethod: String,
    val tableNumber: Int,
    val orderMenuDetailList: List<OrderMenuResponseDto>
)