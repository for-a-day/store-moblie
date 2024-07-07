package com.nagane.table.data.model

data class Basket(
    var basketNo : Int,
    var menuNo : Long,
    var menuName : String,
    var quantity: Int
)

data class BasketItems(
    val basketList: List<Basket>
)
