package com.nagane.table.ui.screen.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.ApiResponse
import com.nagane.table.data.api.RetrofitClient
import com.nagane.table.data.model.TableLogin
import com.nagane.table.data.table.AppDatabase
import com.nagane.table.ui.main.Screens
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// context 통해 시스템 리소스 접근해야 하므로, AndroidViewModel 사용
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("table_prefs", Context.MODE_PRIVATE)

    fun checkIfTableExists(): Boolean {
        val token = sharedPreferences.getString("jwt_token", null)
        return token != null
    }

    fun loginTable(tableLogin: TableLogin, onResult: (ApiResponse<Any>) -> Unit) {
        viewModelScope.launch {
            try {
                // RetrofitClient를 사용하여 API 호출
                RetrofitClient.makeApiCall(RetrofitClient.apiService.loginTable(tableLogin)) { response ->
                    if (response != null) {
                        // 성공적인 경우 데이터 저장 등의 처리
                        sharedPreferences.edit().apply {
                            putString("jwt_token", "임시 토큰")
                            putString("tableCode", tableLogin.tableCode)
                            putString("storeCode", tableLogin.storeCode)
                            putString("tableNumber", tableLogin.tableNumber.toString())
                            putString("tableName", tableLogin.tableName)
                            apply()
                        }
                        onResult(response)
                    } else {
                        onResult(ApiResponse(statusCode = 404, message = "테이블 코드나 가맹점 코드를 확인해주세요."))
                    }
                }
            } catch (e: Exception) {
                // 예외 발생 시 처리
                onResult(ApiResponse(statusCode = 500, message = "서버와의 통신에 실패했습니다."))
            }
        }
    }

//    // Coroutine에서 context를 안전하게 사용하기 위해 withContext 사용
//    private suspend fun loginTableApi(tableLogin: TableLogin): ApiResponse<Any>? {
//        return withContext(Dispatchers.IO) {
//
//            ApiResponse(statusCode = 200, message = "로그인 성공", data = null)
//        }
//    }
}