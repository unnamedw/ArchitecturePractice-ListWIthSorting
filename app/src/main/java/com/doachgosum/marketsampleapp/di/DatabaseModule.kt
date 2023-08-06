package com.doachgosum.marketsampleapp.di

import android.content.Context
import androidx.room.Room
import com.doachgosum.marketsampleapp.data.local.AppDatabase

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