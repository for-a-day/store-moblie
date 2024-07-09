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
import com.nagane.table.data.model.MenuDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val _categories = mutableStateOf<List<Category>>(listOf(Category(0, "전체보기")))
    val categories: State<List<Category>> = _categories
    private val _menus = mutableStateOf<List<Menu>>(mutableListOf())
    val menus: State<List<Menu>> = _menus
    private val _menuDetail = mutableStateOf<MenuDetail>(MenuDetail(1,
        "너무너무 진짜진짜 맛있는 주인장 추천 특선 케이크",
        32000,
        "",
        "아주 달콤하고, 아주 달콤하다. 혀를 녹여버릴 것 같은 단맛에 저항 하다보면, 어느새 접시 위에 눈 녹듯 사라져 있는 환상의 디저트. 진짜 맛있다. 정말 맛있다. 점장 추천 메뉴. 진짜 너무 정말 맛있으니까 꼭 먹어 주었으면 좋겠다. 두 번 먹어라.",
        false))
    val menuDetail : State<MenuDetail> = _menuDetail


    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("table_prefs", Context.MODE_PRIVATE)
    val tableNumber = sharedPreferences.getString("tableNumber", "-1")
    val storeCode = sharedPreferences.getString("storeCode", "-1")
    val tableName = sharedPreferences.getString("tableName", "-1")

    init {
        fetchCategories()
    }

    // 카테고리 데이터 가져오기
    private fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("API_INFO", "가맹점 코드 가져오기 : ${storeCode}")
            try {
                val response = RetrofitClient.apiService.getCategories()
                if (response.statusCode == 200) {
                    val categoryList = response.data?.categoryList ?: emptyList()
                    val allCategories = listOf(Category(0, "전체보기")) + categoryList
                    _categories.value = allCategories

                    fetchMenus(categoryNo = allCategories[0].categoryNo)
                } else {

                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "카테고리 정보 가져오기 실패 : ${e.message}")
            }
        }
    }

    fun fetchMenus(
        categoryNo : Long
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

    fun fetchMenuDetail(
        menuNo: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("API_INFO", "선택한 메뉴 상세정보 가져오기")
                val response = storeCode?.let {
                    Log.d("API_INFO", "선택한 메뉴 상세정보 가져오기 $it $menuNo")
                    RetrofitClient.apiService.getMenuDetail(it, menuNo)
                }
                if (response != null) {
                    Log.d("API_INFO", "메뉴 상세 정보 가져오기 성공 : ${response.message}")
                    if (response.statusCode == 200) {
                        val nowMenuDetail = response.data?.let {
                            it["menu"]?.let { menu ->
                                MenuDetail(
                                    menu.menuNo,
                                    menu.menuName,
                                    menu.price,
                                    menu.imageByte,
                                    menu.description,
                                    menu.soldOut
                                )
                            }
                        }
                        if (nowMenuDetail != null) {
                            _menuDetail.value = nowMenuDetail
                        }
                        Log.d("API_INFO", "메뉴 상세 정보 가져오기 성공 : ${menuDetail}")
                    } else {

                    }
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "메뉴 상세 정보 가져오기 실패 : ${e.message}")
            }
        }
    }
}