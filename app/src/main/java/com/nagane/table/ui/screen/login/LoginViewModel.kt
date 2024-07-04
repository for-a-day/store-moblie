package com.nagane.table.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.ApiResponse
import com.nagane.table.data.api.NaganeAPI
import com.nagane.table.data.api.loginTableApi
import com.nagane.table.data.entity.StoreTableEntity
import com.nagane.table.data.table.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val api: NaganeAPI,  // NaganeAPI 인스턴스를 주입받습니다.
    private val db: AppDatabase // AppDatabase 인스턴스를 주입받습니다.
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _loginResult = MutableStateFlow<ApiResponse<Any>?>(null)
    val loginResult: StateFlow<ApiResponse<Any>?> = _loginResult

    init {
        checkIfLoggedIn()
    }

    private fun checkIfLoggedIn() {
        viewModelScope.launch {
            // 데이터베이스에서 StoreTableEntity 조회
            val storeTable = db.storeTableDao().getAnyStoreTable()

            // StoreTableEntity가 있으면 로그인 상태로 설정
            _isLoggedIn.value = storeTable != null
        }
    }

    fun login(tableCode: String, storeCode: String, tableNumber: Int, tableName: String) {
        viewModelScope.launch {
            loginTableApi(tableCode, storeCode, tableNumber, tableName) { response ->
                _loginResult.value = response
                if (response is ApiResponse.Success && response.statusCode == 200) {
                    val storeTable = StoreTableEntity(
                        tableCode = tableCode,
                        storeCode = storeCode,
                        tableNumber = tableNumber,
                        tableName = tableName
                    )
                    insertTableInfo(storeTable)
                }
            }
        }
    }

    private fun insertTableInfo(storeTable : StoreTableEntity) {
        viewModelScope.launch {
            db.storeTableDao().insert(storeTable)
        }
    }
}