package com.nagane.table.data.model

data class TestResponse(
    val statusCode: Int,
    val message: String,
//    val data : Map<String, Any>?
) {
    companion object {
         fun fromJson(json: Map<String, Any>) : TestResponse = TestResponse(
             statusCode = json["statusCode"] as Int,
             message = json["message"] as String,
//             data = json["data"] as Map<String, Any>?
         )
    }
}
