package com.nagane.table.data.model

data class Category(
    val categoryNo: Int?,
    val categoryName: String
)

data class CategoryResponse(
    val statusCode: Int,
    val message: String,
    val data: CategoryData
)

data class CategoryData(
    val categoryList: List<Category>
)
