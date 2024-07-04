package com.nagane.table.data.api

import com.nagane.table.data.model.Category
import com.nagane.table.data.model.CategoryData
import com.nagane.table.data.model.TableLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NaganeAPI {
    @POST("to")
    fun loginTable(
        @Body requestBody: TableLogin
    ): Call<ApiResponse<Any>>

    @GET("/to/category")
    suspend fun getCategories(
    ): ApiResponse<CategoryData>
}