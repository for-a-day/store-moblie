package com.nagane.table.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nagane.table.data.entity.StoreTableEntity

/** 테이블 오더 로컬 데이터 소스 */
@Dao
interface StoreTableDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storeTable: StoreTableEntity)

    @Query("SELECT * FROM `StoreTable` WHERE tableCode = :tableCode")
    suspend fun getTableByCode(tableCode: String): StoreTableEntity?
}