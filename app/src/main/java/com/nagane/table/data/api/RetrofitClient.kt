package com.nagane.table.data.api

import android.content.ContentValues.TAG
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:9001/"

    private val okHttpClient = OkHttpClient.Builder().build()

    val apiService: NaganeAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaganeAPI::class.java)
    }

    fun <T> makeApiCall(
        call: Call<T>,
        onResult: (T?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("API_CALL", "onResponse 성공: $responseBody")
                    onResult(responseBody)
                } else {
                    Log.d("API_CALL", "onResponse 실패: ${response.code()}")
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("API_CALL", "onFailure 실패", t)
                onResult(null)
            }
        })
    }
}