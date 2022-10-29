package com.example.recordnotebook.di

import com.example.domain.usecases.SaveLoginUserUseCase
import com.example.domain.usecases.loginscreen.GetLoginUserUseCase
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.domain.usecases.mainscreen.GetUserNotatesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetLoginUserUseCase(get()) }
    factory { SaveLoginUserUseCase(get()) }
    factory { VerifyLoginUserCase(get()) }
    factory { GetUserNotatesUseCase(get()) }
}