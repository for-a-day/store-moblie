package com.nagane.table.data.api

import android.util.Log
import com.nagane.table.data.model.TableLogin
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
    val requestBody = TableLogin(tableCode, storeCode, tableNumber, tableName)
    val call: Call<ApiResponse<Any>> = RetrofitClient.apiService.loginTable(requestBody)
    RetrofitClient.makeApiCall(call, onResult)
}