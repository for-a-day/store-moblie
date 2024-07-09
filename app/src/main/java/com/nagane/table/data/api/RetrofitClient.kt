package com.nagane.table.data.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun gsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer { json, typeOfT, context ->
                    LocalDateTime.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                    )
                })
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer { json, typeOfT, context ->
                    LocalDate.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                })
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonDeserializer { json, typeOfT, context ->
                    LocalTime.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                    )
                })
            .create()

        return GsonConverterFactory.create(gson)
    }

    fun <T> makeApiCall(
        call: Call<T>,
        onResult: (T?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.d("API_CALL", "onResponse 성공: $responseBody")
                } else {
                    Log.d("API_CALL", "onResponse 실패: ${response.code()}")
                }
                onResult(responseBody)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.d("API_CALL", "onFailure 실패", t)
                onResult(null)
            }
        })
    }
}