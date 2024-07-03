/*
package com.nagane.table.data.api

import com.nagane.table.data.network.LoginApiService
import com.nagane.table.data.repository.LoginRepository
import com.nagane.table.data.repository.NetworkLoginRepository
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

interface AppContainer {
    val loginRepository: LoginRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "http://localhost:9001/"

    private val retrofit : Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

    private val retrofitService: LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java)
    }

    override val loginRepository: LoginRepository by lazy {
        NetworkLoginRepository(retrofitService)
    }
*/
