package com.nagane.table.data.api

import com.nagane.table.data.model.CategoryData
import com.nagane.table.data.model.MenuData
import com.nagane.table.data.model.MenuDetail
import com.nagane.table.data.model.Order
import com.nagane.table.data.model.OrderCreateDto
import com.nagane.table.data.model.OrderResponseList
import com.nagane.table.data.model.TableAdminLogin
import com.nagane.table.data.model.TableCode
import com.nagane.table.data.model.TableLogin
import com.nagane.table.data.model.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface NaganeAPI {
    // 테이블 기기 등록
    @POST("to")
    suspend fun loginTable(
        @Body requestBody: TableLogin
    ): Response<ApiResponse<TokenResponse>>

    // 카테고리 정보 받아오기
    @GET("/to/category")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): ApiResponse<CategoryData>

    // 메뉴 리스트 받아오기
    @GET("/to/menu/list")
    suspend fun getMenuList(
        @Query("storeCode") storeCode : String,
        @Query("categoryNo") categoryNo : Long,
        @Header("Authorization") token: String
    ): ApiResponse<MenuData>

    // 메뉴 상세 정보 받아오기
    @GET("/to/menu")
    suspend fun getMenuDetail(
        @Query("storeCode") storeCode : String,
        @Query("menuNo") menuNo : Long,
        @Header("Authorization") token: String
    ) : ApiResponse<Map<String, MenuDetail>>

    // 주문 신규 등록
    @POST("/to/order")
    suspend fun createOrder(
        @Body responseBody : OrderCreateDto,
        @Header("Authorization") token: String
    ): Response<ApiResponse<Order>>

    // 해당 테이블 현재 주문 내역 조회
    @GET("/to/order")
    suspend fun getOrderList(
        @Query("tableCode") tableCode : String,
        @Header("Authorization") token: String
    ) : ApiResponse<OrderResponseList>

    // 관리자 모드 로그인
    @POST("/to/admin")
    suspend fun loginAdmin(
        @Body requestBody: TableAdminLogin,
        @Header("Authorization") token: String
    ): Response<ApiResponse<Any>>

    // 해당 테이블 오더 비활성화 요청
    @PUT("/to/admin")
    suspend fun disconnectTable(
        @Body requestBody: TableCode,
        @Header("Authorization") token: String
    ): Response<ApiResponse<Any>>

}