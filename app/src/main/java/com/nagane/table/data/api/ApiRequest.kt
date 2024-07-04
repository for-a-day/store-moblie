package com.nagane.table.data.api

import android.util.Log
import com.nagane.table.data.model.TableLoginDto
import com.nagane.table.di.data.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ApiRequest"

// 공통 API 요청 처리 함수
fun <T> apiRequest(
    call: Call<ApiResponse<T>>,
    onResult: (ApiResponse<T>?) -> Unit
) {
    call.enqueue(object : Callback<ApiResponse<T>> {
        override fun onResponse(call: Call<ApiResponse<T>>, response: Response<ApiResponse<T>>) {
            val apiResponse = response.toApiResponse()
            Log.d(TAG, "onResponse: $apiResponse")
            onResult(apiResponse)
        }

        override fun onFailure(call: Call<ApiResponse<T>>, t: Throwable) {
            Log.d(TAG, "onFailure 실패", t)
            onResult(ApiResponse.Failure.Exception(t))
        }
    })
}

// Response<ApiResponse<T>> 객체를 ApiResponse<T> 타입으로 변환하는 확장 함수
fun <T> Response<ApiResponse<T>>.toApiResponse(): ApiResponse<T> {
    return if (isSuccessful) {
        body()?.let { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> ApiResponse.Success(
                    statusCode = code(),
                    message = message(),
                    data = apiResponse.data,
                    headers = headers().toMultimap().mapValues { it.value.joinToString(", ") }
                )
                is ApiResponse.Failure.ClientError -> ApiResponse.Failure.ClientError(
                    statusCode = apiResponse.statusCode,
                    message = apiResponse.message
                )
                is ApiResponse.Failure.ServerError -> ApiResponse.Failure.ServerError(
                    statusCode = apiResponse.statusCode,
                    message = apiResponse.message
                )
                is ApiResponse.Failure.Exception -> ApiResponse.Failure.Exception(
                    exception = apiResponse.exception
                )
            }
        } ?: ApiResponse.Failure.ServerError(
            statusCode = code(),
            message = "Empty response body"
        )
    } else {
        val errorBody = errorBody()?.string() ?: "Unknown error"
        if (code() in 400..499) {
            ApiResponse.Failure.ClientError(
                statusCode = code(),
                message = errorBody
            )
        } else {
            ApiResponse.Failure.ServerError(
                statusCode = code(),
                message = errorBody
            )
        }
    }
}

// loginTableApi 함수
fun loginTableApi(
    tableCode: String,
    storeCode: String,
    tableNumber: Int,
    tableName: String,
    onResult: (ApiResponse<Any>?) -> Unit
) {
    val requestBody = TableLoginDto(tableCode, storeCode, tableNumber, tableName)
    val call: Call<ApiResponse<Any>> = RetrofitClient.apiService.loginTable(requestBody)
    apiRequest(call, onResult)
}

