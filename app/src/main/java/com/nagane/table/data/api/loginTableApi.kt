package com.nagane.table.data.api

import com.nagane.table.data.model.TableLogin
import retrofit2.Call

private const val TAG = "ApiRequest"

fun loginTableApi(
    tableLogin: TableLogin,
    onResult: (ApiResponse<Any>?) -> Unit
) {
    val call: Call<ApiResponse<Any>> = RetrofitClient.apiService.loginTable(tableLogin)
    RetrofitClient.makeApiCall(call, onResult)
}

/*
*             try {
                val response = RetrofitClient.apiService.loginTable(tableLogin)
                if (response.statusCode == 200) {
                    val token = response.data.toString()
                    sharedPreferences.edit().putString("jwt_token", token).apply()
                    sharedPreferences.edit().putString("tableCode", tableLogin.tableCode).apply()
                    sharedPreferences.edit().putString("storeCode", tableLogin.storeCode).apply()
                    sharedPreferences.edit().putString("tableNumber", tableLogin.tableNumber.toString()).apply()
                    sharedPreferences.edit().putString("tableName", tableLogin.tableName).apply()
                }
            } catch (e: Exception) {

            }
* */