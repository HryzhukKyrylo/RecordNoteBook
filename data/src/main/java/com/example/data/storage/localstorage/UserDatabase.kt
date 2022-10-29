package com.example.data.storage.localstorage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.storage.models.UserNotateDTO

@Database(entities = [UserNotateDTO::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserNotateDao

//    companion object {
//
//        @Volatile
//        private var INSTANCE: UserDatabase? = null
//        fun getDatabase(context: Context): UserDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDatabase::class.java,
//                    "user_data_database"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
}