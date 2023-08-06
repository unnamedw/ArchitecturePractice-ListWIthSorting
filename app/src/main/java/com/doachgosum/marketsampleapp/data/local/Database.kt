package com.doachgosum.marketsampleapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun marketDao(): MarketDao
}