package com.nagane.table.data.model

data class Menu(
    val menuNo : Long,
    val menuName: String,
    val price : Int,
    val imageByte: String,
    val soldOut : Boolean
)

data class MenuData(
    val menuList: List<Menu>
)

data class MenuDetail(
    val menuNo : Long,
    val menuName: String,
    val price : Int,
    val imageByte: String,
    val description: String,
    val soldOut : Boolean
)

data class MenuDetailData(
    val menu: MenuDetail
)
