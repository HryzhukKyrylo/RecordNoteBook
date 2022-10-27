package com.example.recordnotebook.di

import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.preferences.SharedPreferencesStorage
import com.example.data.storage.preferences.SharedPreferencesStorageImpl
import com.example.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> {
        UserRepositoryImpl(prefStorage = get())
    }

    single<SharedPreferencesStorage> {
        SharedPreferencesStorageImpl(context = get())
    }
}