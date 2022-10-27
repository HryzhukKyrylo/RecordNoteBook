package com.example.recordnotebook.di

import com.example.domain.usecases.loginscreen.GetLoginUserUseCase
import com.example.domain.usecases.SaveLoginUserUseCase
import com.example.domain.usecases.loginscreen.VerifyLoginUserCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetLoginUserUseCase)
    factoryOf(::SaveLoginUserUseCase)
    factoryOf(::VerifyLoginUserCase)
}