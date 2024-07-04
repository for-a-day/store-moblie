package com.nagane.table.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.api.RetrofitClient
import com.nagane.table.data.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _categories = mutableStateOf<List<Category>>(mutableListOf(Category(0, "전체보기")))
    val categories: State<List<Category>> = _categories

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getCategories()
                if (response.statusCode == 200) {
                    val categoryList = response.data?.categoryList ?: emptyList()
                    val allCategories = mutableListOf(Category(0, "전체보기"))
                    allCategories.addAll(categoryList)
                    _categories.value = allCategories
                } else {

                }
            } catch (e: Exception) {

            }
        }
    }
}