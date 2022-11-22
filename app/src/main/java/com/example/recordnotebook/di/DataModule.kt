package com.example.recordnotebook.di

import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import org.koin.dsl.module


val dataModule = module {

    factory<UserRepository> {
        UserRepositoryImpl(
            context = get(),
            localStorage = get(),
            preferencesStorage = get(),
        )
    }
}