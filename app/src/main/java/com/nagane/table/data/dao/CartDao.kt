package com.nagane.table.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nagane.table.data.entity.CartEntity

/** 장바구니 로컬 데이터 소스 */
@Dao
interface CartDao {
    // 새로 테이블 데이터 집어넣음
    // 만약 기존에 값 있으면 대체함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: CartEntity)

    // 메뉴 코드로 이미 해당 메뉴가 장바구니에 들어있는지 검증
    @Query("SELECT * FROM `Cart` WHERE menuNo = :menuNo")
    suspend fun getCartByMenuNo(menuNo: Long): CartEntity?

    // 현재 장바구니 목록 전체 다 불러오기
    @Query("SELECT * FROM `Cart`")
    suspend fun getCart() : CartEntity?

    // 장바구니 목록 전체 삭제
    @Query("DELETE FROM `Cart`")
    suspend fun deleteCart()

    // 해당 장바구니 항목 삭제
    @Query("DELETE FROM `Cart` WHERE cartNo = :cartNo")
    suspend fun deleteByMenuNo(cartNo: Long)

    // 장바구니 번호에 해당하는 CartEntity를 선택하여 수량을 업데이트하는 쿼리
    @Query("UPDATE `Cart` SET quantity = :newQuantity WHERE cartNo = :cartNo")
    suspend fun updateQuantity(cartNo: Long, newQuantity: Int)

}