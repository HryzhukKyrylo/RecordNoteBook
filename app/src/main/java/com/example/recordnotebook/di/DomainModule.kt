package com.example.recordnotebook.di

import com.example.domain.usecases.createscreen.SaveUserNotateUseCase
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import com.example.domain.usecases.mainscreen.GetUserNotatesUseCase
import com.example.domain.usecases.mainscreen.RemoveUserNotateUseCase
import com.example.domain.usecases.signupscreen.SaveLoginUserUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { SaveLoginUserUseCase(repository = get()) }
    factory { SaveUserNotateUseCase(repository = get()) }
    factory { VerifyLoginUserCase(repository = get()) }
    factory { GetUserNotatesUseCase(repository = get()) }
    factory { RemoveUserNotateUseCase(repository = get()) }
}