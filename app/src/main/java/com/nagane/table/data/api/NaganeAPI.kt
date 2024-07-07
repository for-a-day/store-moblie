package com.nagane.table.data.api

import com.nagane.table.data.model.Category
import com.nagane.table.data.model.CategoryData
import com.nagane.table.data.model.MenuData
import com.nagane.table.data.model.MenuDetail
import com.nagane.table.data.model.MenuDetailData
import com.nagane.table.data.model.TableLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NaganeAPI {
    // 테이블 기기 등록
    @POST("to")
    fun loginTable(
        @Body requestBody: TableLogin
    ): Call<ApiResponse<Any>>

    // 카테고리 정보 받아오기
    @GET("/to/category")
    suspend fun getCategories(
    ): ApiResponse<CategoryData>

    // 메뉴 리스트 받아오기
    @GET("/to/menuList")
    suspend fun getMenuList(
        @Query("storeCode") storeCode : String,
        @Query("categoryNo") categoryNo : Int,
    ): ApiResponse<MenuData>

    // 메뉴 상세 정보 받아오기
    @GET("/to/menu")
    suspend fun getMenuDetail(
        @Query("storeCode") storeCode : String,
        @Query("menuNo") menuNo : Int,
    ) : ApiResponse<Map<String, MenuDetail>>
}