package com.example.recordnotebook.di

import com.example.data.parser.ResultParserImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.parser.ResultParser
import com.example.domain.repository.UserRepository
import org.koin.dsl.module


val dataModule = module {

    factory<UserRepository> {
        UserRepositoryImpl(
            localStorage = get(),
            preferencesStorage = get(),
        )
    }

    single<ResultParser> {
        ResultParserImpl(context = get())
    }
}