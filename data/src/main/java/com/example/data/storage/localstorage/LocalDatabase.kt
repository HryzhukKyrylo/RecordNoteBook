package com.example.data.storage.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.storage.models.UserNotateDTO

@Database(entities = [UserNotateDTO::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserNotateDao
}