package com.nagane.table.ui.screen.login

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.loginTableApi
import com.nagane.table.data.entity.StoreTableEntity
import com.nagane.table.data.table.AppDatabase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    suspend fun checkIfTableExists(): Boolean {
        val dao = AppDatabase.getDatabase(context).storeTableDao()
        val count = dao.getCount()
        // return count > 0
        return true
    }

    fun login(
        tableCode: String,
        storeCode: String,
        tableNumber: Int,
        tableName: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            loginTableApi(tableCode, storeCode, tableNumber, tableName) { response ->
                if (response != null && response.statusCode == 200) {
                    // 데이터베이스 인스턴스 가져오기
                    val db = AppDatabase.getDatabase(context)
                    val storeTableDao = db.storeTableDao()

                    // 데이터베이스에 저장
                    val storeTable = StoreTableEntity(
                        tableCode = tableCode,
                        storeCode = storeCode,
                        tableNumber = tableNumber,
                        tableName = tableName
                    )

                    // 데이터 삽입
                    viewModelScope.launch {
                        storeTableDao.insert(storeTable)
                        onSuccess()
                    }
                } else {
                    onError(response?.message ?: "Failed to communicate with server")
                }
            }
        }
    }
}