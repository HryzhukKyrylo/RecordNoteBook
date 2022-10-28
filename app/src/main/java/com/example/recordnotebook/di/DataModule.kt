package com.example.recordnotebook.di

import androidx.room.Room
import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.localstorage.LocalDatabase
import com.example.data.storage.localstorage.TABLE_NAME
import com.example.data.storage.localstorage.UserNotateDao
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.data.storage.preferences.SharedPreferencesStorageImpl
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserNotateDao> {
        val userDB = Room.databaseBuilder(
            get(),
            LocalDatabase::class.java,
            TABLE_NAME
        ).build()

        userDB.userDao()
    }

    single<UserRepository> {
        UserRepositoryImpl(prefStorage = get(), localStorage = get())
    }

    single<SharedPreferencesStorage> {
        SharedPreferencesStorageImpl(context = get())
    }
}