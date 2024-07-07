package com.nagane.table.ui.screen.home

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.dao.BasketDao
import com.nagane.table.data.model.Basket
import com.nagane.table.data.table.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val basketDao : BasketDao = AppDatabase.getDatabase(application).basketDao()

    private val _basketItems = mutableStateOf<List<Basket>>(mutableListOf())
    val basketItems: State<List<Basket>> = _basketItems

    fun fetchBasketItems() {
        viewModelScope.launch {
            loadBasketItems()
        }
    }

    private suspend fun loadBasketItems() {
        val basketEntity = basketDao.getBasket()

        if (basketEntity != null) {
            _basketItems.value = listOf(
                Basket(
                    basketNo = basketEntity.basketNo,
                    menuNo = basketEntity.menuNo,
                    menuName = basketEntity.menuName,
                    quantity = basketEntity.quantity
                )
            )
        } else {
            _basketItems.value = emptyList()
        }
    }
}