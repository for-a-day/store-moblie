package com.nagane.table.ui.screen.home

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.RetrofitClient
import com.nagane.table.data.model.Category
import com.nagane.table.data.model.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val _categories = mutableStateOf<List<Category>>(mutableListOf(Category(0, "전체보기")))
    val categories: State<List<Category>> = _categories
    private val _menus = mutableStateOf<List<Menu>>(mutableListOf())
    val menus: State<List<Menu>> = _menus

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("table_prefs", Context.MODE_PRIVATE)
    val tableNumber = sharedPreferences.getString("tableNumber", "-1")
    val storeCode = sharedPreferences.getString("storeCode", "-1")
    val tableName = sharedPreferences.getString("tableName", "-1")

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("API_INFO", "가맹점 코드 가져오기 : ${storeCode}")
            try {
                val response = RetrofitClient.apiService.getCategories()
                if (response.statusCode == 200) {
                    val categoryList = response.data?.categoryList ?: emptyList()
                    val allCategories = mutableListOf(Category(0, "전체보기"))
                    allCategories.addAll(categoryList)
                    _categories.value = allCategories

                    fetchMenus(categoryNo = categories.value[0].categoryNo)
                } else {

                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "카테고리 정보 가져오기 실패 : ${e.message}")
            }
        }
    }

    fun fetchMenus(
        categoryNo : Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("API_INFO", "메뉴 리스트 가져오기")
                val response = storeCode?.let { RetrofitClient.apiService.getMenuList(it, categoryNo) }
                if (response != null) {
                    Log.d("API_INFO", "메뉴 리스트 가져오기 성공 : ${response.message}")
                    if (response.statusCode == 200) {
                        _menus.value = response.data?.menuList ?: emptyList()
                    } else {

                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "메뉴 리스트 가져오기 실패 : ${e.message}")
            }
        }
    }
}