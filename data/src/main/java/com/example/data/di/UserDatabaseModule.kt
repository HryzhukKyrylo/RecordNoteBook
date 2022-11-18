package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.storage.localstorage.LocalStorage
import com.example.data.storage.localstorage.LocalStorageImpl
import com.example.data.storage.localstorage.UserDatabase
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.data.storage.preferences.SharedPreferencesStorageImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val userDatabaseModule = module {
    fun provideDatabase(application: Application): UserDatabase {
        return Room.databaseBuilder(
            application,
            UserDatabase::class.java,
            "user_data_database"
        ).build()
    }

    single<UserDatabase> {
        provideDatabase(androidApplication())
    }

    single<LocalStorage> {
        LocalStorageImpl(get(), get())
    }

    single<SharedPreferencesStorage> {
        SharedPreferencesStorageImpl(context = get())
    }


}