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
import com.nagane.table.data.dao.CartDao
import com.nagane.table.data.entity.CartEntity
import com.nagane.table.data.entity.toCart
import com.nagane.table.data.model.Cart
import com.nagane.table.data.model.CartCreateDto
import com.nagane.table.data.model.OrderCreateDto
import com.nagane.table.data.model.OrderMenuDto
import com.nagane.table.data.table.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartDao : CartDao = AppDatabase.getDatabase(application).cartDao()

    private val _cartItems = mutableStateOf<List<Cart>>(mutableListOf())
    val cartItems: State<List<Cart>> = _cartItems

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences("table_prefs", Context.MODE_PRIVATE)
    val storeCode = sharedPreferences.getString("storeCode", "-1")
    val tableCode = sharedPreferences.getString("tableCode", "-1")

    fun fetchCartItems() {
        viewModelScope.launch {
            loadCartItems()
        }
    }

    // 장바구니 아이템 db로부터 불러오기
    private suspend fun loadCartItems() {
        try {
            val cartEntities : List<CartEntity> = cartDao.getCart()

            _cartItems.value = cartEntities.map { it.toCart() }

        } catch (e : Exception) {
            Log.e("loadCartItems", "장바구니 데이터 불러오는 중 문제 발생")
        }
    }

    fun addCart(cart: CartCreateDto, onResult: (Boolean) -> Unit = {}) {
        Log.d("잘 들어옴", "하하")
        viewModelScope.launch {
            insertCart(cart, onResult)
        }
    }

    // 장바구니에서 선택한 메뉴 삭제
    fun deleteCartMenu(cartNo: Long) {
        Log.d("deleteCartMenu", "$cartNo 항목을 장바구니에서 삭제합니다")
        viewModelScope.launch {
            cartDao.deleteByCartNo(cartNo)
            loadCartItems()
        }
    }

    private suspend fun deleteCartAllMenu() {
        Log.d("deleteCartAllMenu", "모든 항목을 장바구니에서 삭제합니다")
        cartDao.deleteCart()
        loadCartItems()
    }

    fun updateCartQuantity(cartNo: Long, changedQuantity: Int) {
        viewModelScope.launch {
            changeCartQuantity(cartNo, changedQuantity)
        }
    }

    // 장바구니 메뉴 선택 숫자 변경
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

    // 카트에 메뉴 추가
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

        // 주문 요청
    fun createOrder(
        paymentCase : Int, dineCase: Int
    ) {
        // 카트에 들어있던 정보들 orderMenuCreateDto 형식으로 변환
        val orderMenuCreateList = cartItems.value.map { cart ->
            OrderMenuDto(
                menuNo = cart.menuNo,
                menuName = cart.menuName,
                quantity = cart.quantity
            )
        }

        if (tableCode != null && storeCode != null) {
            val orderCreateDto = OrderCreateDto(
                amount = cartItems.value.sumOf { it.price * it.quantity },
                paymentMethod = when (paymentCase) {
                    1 -> "CARD"
                    else -> "CASH"
                },
                storeCode = storeCode,
                tableCode = tableCode,
                orderCase = when (dineCase) {
                    1 -> "DINE_IN"
                    else -> "TAKEOUT"
                },
                orderMenuList = orderMenuCreateList
            )

            Log.d("createOrder", "$orderCreateDto")
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = RetrofitClient.apiService.createOrder(orderCreateDto)
                    if (response != null) {
                        Log.d("API_INFO", "주문 성공 : ${response.message}")
                    }
                    delay(9003L)
                    deleteCartAllMenu()
                } catch (e: Exception) {
                    Log.e("API_ERROR", "주문 실패 : ${e.message}")
                }
            }
        } else {
            Log.e("API_ERROR", "주문 실패 : 가맹점 코드나 테이블 코드 중 null 값 발견")
        }
    }
}