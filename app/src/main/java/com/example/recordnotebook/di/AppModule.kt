package com.example.recordnotebook.di

import com.example.recordnotebook.ui.createscreen.CreateScreenViewModel
import com.example.recordnotebook.ui.mainscreen.MainScreenViewModel
import com.example.recordnotebook.ui.signinscreen.SignInScreenViewModel
import com.example.recordnotebook.ui.signupscreen.SignUpScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInScreenViewModel(verifyLoginUserCase = get()) }
    viewModel { SignUpScreenViewModel(saveLoginUserUseCase = get()) }
    viewModel { MainScreenViewModel(getUserNotatesUseCase = get(), get()) }
    viewModel { CreateScreenViewModel(saveUserNotateUseCase = get()) }
}