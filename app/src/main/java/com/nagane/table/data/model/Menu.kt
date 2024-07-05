package com.nagane.table.data.model

data class Menu(
    val menuNo : Int,
    val menuName: String,
    val price : Int,
    val soldOut : Boolean
)


data class MenuData(
    val menuList: List<Menu>
)