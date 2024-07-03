//package com.nagane.table.data.source
//
//import android.util.Log
//import com.nagane.table.data.api.ApiResponse
//import com.nagane.table.data.api.TestAPI
//import com.nagane.table.data.model.TestResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//
//private const val TAG: String = "REMOTE DATA SOURCE"
//private val retrofit: Retrofit = Retrofit.Builder().baseUrl("http://localhost:9001/")
//    .addConverterFactory(GsonConverterFactory.create()).build()
//private val retrofitAPI: TestAPI = retrofit.create(TestAPI::class.java)
//
//fun searchImage(): List<TestResponse> {
//    val results = mutableListOf<TestResponse>()
//    val call: Call<ApiResponse<Any>> = retrofitAPI.loginTable()
//
//    call.enqueue(object : Callback<Map<String, Any>?> {
//        override fun onResponse(
//            call: Call<Map<String, Any>?>,
//            response: Response<Map<String, Any>?>,
//        ) {
//            Log.d(TAG, "onResponse 성공")
//            if (response.isSuccessful) {
//                val responseBody = response.body()!!
//                val docs = responseBody["documents"] as List<*>
//                for (doc in docs) {
//                    Log.d(TAG, "doc: $doc")
//                    results.add(doc.toString())
//                }
//            }
//        }
//
//        override fun onFailure(call: Call<Map<String, Any>?>, t: Throwable) {
//            Log.d(TAG, "onFailure 실패")
//        }
//    })
//    return results
//}