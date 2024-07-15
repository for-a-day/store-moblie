package com.nagane.table.ui.screen.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.ApiResponse
import com.nagane.table.data.api.RetrofitClient
import com.nagane.table.data.dao.CartDao
import com.nagane.table.data.model.TableAdminLogin
import com.nagane.table.data.model.TableCode
import com.nagane.table.data.model.TableLogin
import com.nagane.table.data.table.AppDatabase
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.util.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// context 통해 시스템 리소스 접근해야 하므로, AndroidViewModel 사용
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("table_prefs", Context.MODE_PRIVATE)
    private val cartDao : CartDao = AppDatabase.getDatabase(application).cartDao()
    private val accessToken = sharedPreferences.getString("accessToken", "-1")

    init {
        // AuthInterceptor의 unauthorizedEvent 관찰 => 발생 시, 데이터 삭제
        AuthInterceptor.unauthorizedEvent.observeForever(Observer { unauthorized ->
            if (unauthorized) {
                deleteData()
            }
        })
    }

    @SuppressLint("StaticFieldLeak")
    private val context: Context = application.applicationContext
    private val apiService = RetrofitClient.create(context)

    fun checkIfTableExists(): Boolean {
        val token = sharedPreferences.getString("tableCode", null)
        return token != null
    }

    fun loginTable(context: Context, tableLogin: TableLogin, onResult: (ApiResponse<Any>) -> Unit) {
        viewModelScope.launch {
            Log.d("loginTable", "$tableLogin 를 통해 로그인합니다.")
            try {
                val response = apiService.loginTable(tableLogin)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.statusCode == 200) {
                        val tokens = responseBody.data?.tokens
                        if (tokens != null) {
//                            // 토큰을 SharedPreferences에 저장
//                            SharedPreferencesManager.saveTokens(
//                                context,
//                                tokens.accessToken,
//                                tokens.refreshToken
//                            )
                            // SharedPreferencesManager.saveAdditionalData(context, tableLogin)
                            Log.d("tokens", tokens.accessToken)
                            // 추가적인 데이터를 SharedPreferences에 저장
                            sharedPreferences.edit().apply {
                                putString("jwt_token", "임시 토큰")
                                putString("tableCode", tableLogin.tableCode)
                                putString("storeCode", tableLogin.storeCode)
                                putString("tableNumber", tableLogin.tableNumber.toString())
                                putString("tableName", tableLogin.tableName)
                                putString("accessToken", tokens.accessToken)
                                putString("refreshToken", tokens.refreshToken)
                                apply()
                            }

                            delay(500L)

                            onResult(ApiResponse(statusCode = 200, message = "로그인 성공", data = responseBody.data))
                        } else {
                            onResult(ApiResponse(statusCode = 404, message = "토큰을 받아올 수 없습니다."))
                        }
                    } else {
                        onResult(ApiResponse(statusCode = 404, message = "테이블 코드나 가맹점 코드를 확인해주세요."))
                    }
                } else {
                    onResult(ApiResponse(statusCode = 404, message = "테이블 코드나 가맹점 코드를 확인해주세요."))
                }
            } catch (e: Exception) {
                onResult(ApiResponse(statusCode = 500, message = "서버와의 통신에 실패했습니다."))
            }
        }
    }

    // 관리자 모드 로그인
    fun loginAdmin(tableAdminLogin: TableAdminLogin, onResult: (ApiResponse<Any>) -> Unit) {
        Log.d("loginAdmin", "$tableAdminLogin 를 통해 로그인합니다.")
        viewModelScope.launch {
            try {
                val response = apiService.loginAdmin(tableAdminLogin, "Bearer $accessToken ")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("loginAdmin", "로그인 성공: $responseBody")
                    if (responseBody != null) {
                        onResult(responseBody)
                    } else {
                        Log.e("loginAdmin", "테이블 코드나 가맹점 코드를 확인해주세요.")
                        onResult(ApiResponse(statusCode = 404, message = "테이블 코드나 가맹점 코드를 확인해주세요."))
                    }
                } else {
                    Log.e("loginAdmin", "테이블 코드나 가맹점 코드를 확인해주세요.")
                    onResult(ApiResponse(statusCode = 404, message = "테이블 코드나 가맹점 코드를 확인해주세요."))
                }
            } catch (e : Exception) {
                Log.e("loginAdmin", "서버와의 통신에 실패했습니다. : $e")
                onResult(ApiResponse(statusCode = 500, message = "서버와의 통신에 실패했습니다."))
            }
        }
    }

    private fun deleteData() {
        Log.d("deleteData", "모든 데이터를 삭제합니다")
        viewModelScope.launch {
            sharedPreferences.edit().clear().apply()
            cartDao.deleteCart()
        }
    }

    // 테이블 연결 해제
    fun disConnectTable(tableCode: TableCode, onResult: (ApiResponse<Any>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.disconnectTable(tableCode,"Bearer $accessToken ")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("loginAdmin", "로그인 성공: $responseBody")
                    if (responseBody != null) {
                        deleteData()
                        onResult(responseBody)
                    } else {
                        Log.e("loginAdmin", "테이블 코드를 확인해주세요.")
                        onResult(ApiResponse(statusCode = 404, message = "테이블 코드를 확인해주세요."))
                    }
                } else {
                    Log.e("loginAdmin", "테이블 코드를 확인해주세요.")
                    onResult(ApiResponse(statusCode = 404, message = "테이블 코드를 확인해주세요."))
                }
            } catch (e : Exception) {
                onResult(ApiResponse(statusCode = 500, message = "서버와의 통신에 실패했습니다."))
            }
        }
    }
}