package com.nagane.table.data.model

data class Basket(
    var basketNo : Long,
    var menuNo : Long,
    var menuName : String,
    var price : Int,
    var quantity: Int
)

data class BasketItems(
    val basketList: List<Basket>
)

data class BasketCreateDto(
    var menuNo : Long,
    var menuName : String,
    var price : Int,
    var quantity: Int = 1
)