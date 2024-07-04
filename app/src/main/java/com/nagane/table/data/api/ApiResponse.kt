package com.nagane.table.data.api

/** 선언형 api 응답 처리 */

sealed interface ApiResponse<out T> {

    // API 요청 성공 시
    data class Success<T>(
        val statusCode: Int,
        val message: String,
        val data: T?,
        val headers: Map<String, String>? = null
    ) : ApiResponse<T>

    // 다양한 실패 상황
    sealed interface Failure<T> : ApiResponse<T> {
        // 클라이언트 측 에러
        data class ClientError<T>(
            val statusCode: Int,
            val message: String
        ) : Failure<T>

        // 서버 측 에러
        data class ServerError<T>(
            val statusCode: Int,
            val message: String
        ) : Failure<T>

        // 예외 상황 (로컬라이즈 메시지 가져옴)
        data class Exception<T>(val exception: Throwable) : Failure<T> {
            val message: String? = exception.localizedMessage
        }
    }
}
