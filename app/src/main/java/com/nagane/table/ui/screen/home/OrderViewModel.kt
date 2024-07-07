package com.nagane.table.ui.screen.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nagane.table.data.dao.CartDao
import com.nagane.table.data.entity.CartEntity
import com.nagane.table.data.model.Cart
import com.nagane.table.data.model.CartCreateDto
import com.nagane.table.data.table.AppDatabase
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val cartDao : CartDao = AppDatabase.getDatabase(application).cartDao()

    private val _cartItems = mutableStateOf<List<Cart>>(mutableListOf())
    val cartItems: State<List<Cart>> = _cartItems

    fun fetchCartItems() {
        viewModelScope.launch {
            loadCartItems()
        }
    }

    private suspend fun loadCartItems() {
        try {
            val cartEntity = cartDao.getCart()

            if (cartEntity != null) {
                _cartItems.value = listOf(
                    Cart(
                        cartNo = cartEntity.cartNo ?: 0,
                        menuNo = cartEntity.menuNo,
                        menuName = cartEntity.menuName,
                        quantity = cartEntity.quantity,
                        price = cartEntity.price
                    )
                )
            } else {
                _cartItems.value = emptyList()
            }
        } catch (e : Exception) {

        }
    }

    fun addCart(cart: CartCreateDto, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            insertCart(cart, onResult)
        }
    }

    fun deleteCartMenu(cartNo: Long) {
        viewModelScope.launch {
            cartDao.deleteByMenuNo(cartNo)
            loadCartItems()
        }
    }

    fun updateCartQuantity(cartNo: Long, changedQuantity: Int) {
        viewModelScope.launch {
            changeCartQuantity(cartNo, changedQuantity)
        }
    }

    private suspend fun changeCartQuantity(
        cartNo: Long, changedQuantity: Int
    ) {
        try {
            cartDao.updateQuantity(cartNo, changedQuantity)
            Log.d("CartDao", "정상적으로 업데이트됐습니다 : $changedQuantity")
            // loadCartItems()
        } catch (e : Exception) {

        }
    }

    private suspend fun insertCart(cart: CartCreateDto, onResult: (Boolean) -> Unit) {
        // 메뉴 존재 여부 불러옴
        val nowMenu = cartDao.getCartByMenuNo(cart.menuNo)
        // 해당 메뉴가 존재하지 않을 시, 새로 레코드 삽입
        if (nowMenu == null) {
            try {
                cartDao.insert(CartEntity(cart.menuNo, cart.menuName, cart.price))
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        // 해당 메뉴가 이미 장바구니에 담겨 있을 시, 주문 개수 1 추가
        } else {
            try {
                cartDao.updateQuantity(cartNo = nowMenu.cartNo, newQuantity = nowMenu.quantity + 1)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }

    }
}