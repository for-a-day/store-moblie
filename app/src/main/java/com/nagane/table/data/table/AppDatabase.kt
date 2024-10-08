package com.nagane.table.data.table

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nagane.table.data.dao.CartDao
import com.nagane.table.data.dao.StoreTableDao
import com.nagane.table.data.entity.CartEntity
import com.nagane.table.data.entity.StoreTableEntity

@Database(entities = [StoreTableEntity::class, CartEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storeTableDao(): StoreTableDao
    abstract fun cartDao() : CartDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "store_table_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}