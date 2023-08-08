package com.doachgosum.marketsampleapp.di

import android.content.Context
import androidx.room.Room
import com.doachgosum.marketsampleapp.data.local.AppDatabase

/**
 * TODO("추후 로컬 캐싱 개선 시 DB로 이전")
 * **/
object DatabaseModule {

    private var db: AppDatabase? = null

    fun providesAppDatabase(
        context: Context,
    ): AppDatabase {

        db?.let {
            return it
        } ?: kotlin.run {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app-database",
            ).build().also { db = it }
        }
    }


}