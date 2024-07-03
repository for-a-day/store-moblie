package com.nagane.table.data.api

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ApiRequest"

fun loginTableApi(
    tableCode: String,
    storeCode: String,
    tableNumber: Int,
    tableName: String,
    onResult: (ApiResponse<Any>?) -> Unit
) {
    val requestBody = TableLoginRequestBody(tableCode, storeCode, tableNumber, tableName)
    val call: Call<ApiResponse<Any>> = RetrofitClient.apiService.loginTable(requestBody)

    call.enqueue(object : Callback<ApiResponse<Any>> {
        override fun onResponse(call: Call<ApiResponse<Any>>, response: Response<ApiResponse<Any>>) {
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d(TAG, "onResponse 성공: $responseBody")
                onResult(responseBody)
            } else {
                Log.d(TAG, "onResponse 실패: ${response.code()}")
                onResult(null)
            }
        }

        override fun onFailure(call: Call<ApiResponse<Any>>, t: Throwable) {
            Log.d(TAG, "onFailure 실패", t)
            onResult(null)
        }
    })
}