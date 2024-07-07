package com.nagane.table.data.model

data class Cart(
    var cartNo : Long,
    var menuNo : Long,
    var menuName : String,
    var price : Int,
    var quantity: Int
)

data class CartItems(
    val cartList: List<Cart>
)

data class CartCreateDto(
    var menuNo : Long,
    var menuName : String,
    var price : Int,
    var quantity: Int = 1
)