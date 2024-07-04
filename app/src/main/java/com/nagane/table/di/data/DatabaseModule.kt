package com.nagane.table.di.data

import android.content.Context
import androidx.room.Room
import com.nagane.table.data.dao.StoreTableDao
import com.nagane.table.data.table.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideStoreTableDao(database: AppDatabase): StoreTableDao {
        return database.storeTableDao()
    }
}