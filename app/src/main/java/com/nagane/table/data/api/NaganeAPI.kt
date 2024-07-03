package com.nagane.table.data.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NaganeAPI {
    @POST("to")
    fun loginTable(
        @Body requestBody: TableLoginRequestBody
    ): Call<ApiResponse<Any>>
}