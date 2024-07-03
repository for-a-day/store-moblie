//package com.nagane.table.data.network
//
//import com.nagane.table.data.api.AppContainer
//import com.nagane.table.data.model.BaseResponse
//import com.nagane.table.data.repository.LoginRepository
//import com.nagane.table.data.repository.NetworkLoginRepository
//import retrofit2.Retrofit
//import retrofit2.converter.scalars.ScalarsConverterFactory
//import retrofit2.http.POST
//
//
//interface LoginApiService {
//    @POST("to")
//    fun loginTable() : BaseResponse
//}
//
//class DefaultAppContainer : AppContainer {
//    private val BASE_URL = "http://localhost:9001/"
//
//    private val retrofit : Retrofit = Retrofit.Builder()
//        .addConverterFactory(ScalarsConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .build()
//
//    private val retrofitService: LoginApiService by lazy {
//        retrofit.create(LoginApiService::class.java)
//    }
//
//    override val loginRepository: LoginRepository by lazy {
//        NetworkLoginRepository(retrofitService)
//    }
//
//
//}
//
//object LoginApi {
//    val retrofitService : LoginApiService by lazy {
//        retrofit.create(LoginApiService::class.java)
//    }
//}
