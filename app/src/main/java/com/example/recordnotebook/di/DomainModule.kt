package com.example.recordnotebook.di

import com.example.domain.usecases.SaveLoginUserUseCase
import com.example.domain.usecases.createscreen.SaveUserNotateUseCase
import com.example.domain.usecases.loginscreen.GetLoginUserUseCase
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.domain.usecases.mainscreen.GetUserNotatesUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetLoginUserUseCase(repository = get()) }
    factory { SaveLoginUserUseCase(repository = get()) }
    factory { SaveUserNotateUseCase(repository = get()) }
    factory { VerifyLoginUserCase(repository = get()) }
    factory { GetUserNotatesUseCase(repository = get()) }
}