/*
package com.nagane.table.data.api

import okhttp3.internal.http2.Header

*/
/** 선언형 api 응답 처리 *//*

sealed interface ApiResponse<out T> {

    // api 요청 성공 시
    data class Success<T>(
        val data: T,
        val headers: Header
    ) : ApiResponse<T>

    // 다양한 실패 상황
    sealed interface Failure<T>: ApiResponse<T> {
        // 클라이언트 측 에러
        data class ClientError<T> (
            val statusCode : Int, val message : String
        ) : Failure<T>
        // 서버 측 에러
        data class ServerError<T> (
            val statusCode : Int, val message : String
        ) : Failure<T>
        // 예외 상황(로콜라이즈 메시지 가져옴)
        data class Exception<T>(val exception: Throwable)
            : Failure<T> {
            val message: String? = exception.localizedMessage
        }
    }
}*/
