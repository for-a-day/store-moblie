package com.nagane.table.ui.screen.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.data.dao.BasketDao
import com.nagane.table.data.entity.BasketEntity
import com.nagane.table.data.model.Basket
import com.nagane.table.data.model.BasketCreateDto
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
        try {
            val basketEntity = basketDao.getBasket()

            if (basketEntity != null) {
                _basketItems.value = listOf(
                    Basket(
                        basketNo = basketEntity.basketNo ?: 0,
                        menuNo = basketEntity.menuNo,
                        menuName = basketEntity.menuName,
                        quantity = basketEntity.quantity,
                        price = basketEntity.price
                    )
                )
            } else {
                _basketItems.value = emptyList()
            }
        } catch (e : Exception) {

        }
    }

    fun addBasket(basket: BasketCreateDto, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            insertBasket(basket, onResult)
        }
    }

    fun deleteBasketMenu(basketNo: Long) {
        viewModelScope.launch {
            basketDao.deleteByMenuNo(basketNo)
            loadBasketItems()
        }
    }

    fun updateBasketQuantity(basketNo: Long, changedQuantity: Int) {
        viewModelScope.launch {
            changeBasketQuantity(basketNo, changedQuantity)
        }
    }

    private suspend fun changeBasketQuantity(
        basketNo: Long, changedQuantity: Int
    ) {
        try {
            basketDao.updateQuantity(basketNo, changedQuantity)
            Log.d("BasketDao", "정상적으로 업데이트됐습니다 : $changedQuantity")
            // loadBasketItems()
        } catch (e : Exception) {

        }
    }

    private suspend fun insertBasket(basket: BasketCreateDto, onResult: (Boolean) -> Unit) {
        // 메뉴 존재 여부 불러옴
        val nowMenu = basketDao.getBasketByMenuNo(basket.menuNo)
        // 해당 메뉴가 존재하지 않을 시, 새로 레코드 삽입
        if (nowMenu == null) {
            try {
                basketDao.insert(BasketEntity(basket.menuNo, basket.menuName, basket.price))
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        // 해당 메뉴가 이미 장바구니에 담겨 있을 시, 주문 개수 1 추가
        } else {
            try {
                basketDao.updateQuantity(basketNo = nowMenu.basketNo, newQuantity = nowMenu.quantity + 1)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }

    }
}