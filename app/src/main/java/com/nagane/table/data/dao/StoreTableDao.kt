package com.nagane.table.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nagane.table.data.entity.StoreTableEntity

/** 테이블 오더 로컬 데이터 소스 */
@Dao
interface StoreTableDao {
    // 새로 테이블 데이터 집어넣음
    // 만약 기존에 값 있으면 대체함
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(storeTable: StoreTableEntity)

    // 테이블 코드로 테이블 정보 찾아옴
    @Query("SELECT * FROM `StoreTable` WHERE tableCode = :tableCode")
    suspend fun getTableByCode(tableCode: String): StoreTableEntity?

    // 가맹점 코드 기준으로 테이블 정보 찾아옴(단일 정보만 조회)
    @Query("SELECT * FROM StoreTable WHERE storeCode = :storeCode LIMIT 1")
    suspend fun getStoreTableByCode(storeCode: String): StoreTableEntity?

    // 해당 테이블 코드 외 모든 데이터 삭제
    @Query("DELETE FROM StoreTable WHERE tableCode != :tableCode")
    suspend fun deleteAllExcept(tableCode: String)

    // 테이블 하나만 조회
    @Query("SELECT * FROM StoreTable LIMIT 1")
    suspend fun getAnyStoreTable(): StoreTableEntity?
}