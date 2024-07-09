package com.nagane.table.ui.screen.order

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.RetrofitClient
import com.nagane.table.data.dao.CartDao
import com.nagane.table.data.model.Cart
import com.nagane.table.data.model.Order
import com.nagane.table.data.table.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val _orderList = mutableStateOf<List<Order>>(mutableListOf())
    val orderList : State<List<Order>> = _orderList

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("table_prefs", Context.MODE_PRIVATE)
    val storeCode = sharedPreferences.getString("storeCode", "-1")
    val tableCode = sharedPreferences.getString("tableCode", "-1")

    fun fetchOrderList() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("fetchOrderList", "주문 리스트 데이터를 불러옵니다.")
            try {
                val response = tableCode?.let { RetrofitClient.apiService.getOrderList(it) }

                if (response != null && response.statusCode == 0) {
                    _orderList.value = response.data?.orderList ?: emptyList()
                }
            } catch (e : Exception) {
                Log.e("API_ERROR", "주문 정보 가져오기 실패 : ${e.message}")
            }
        }
    }
}