package com.example.recordnotebook.di

import com.example.recordnotebook.ui.mainscreen.MainScreenViewModel
import com.example.recordnotebook.ui.signinscreen.SignInScreenViewModel
import com.example.recordnotebook.ui.signupscreen.SignUpScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

//    viewModel<LoginScreenViewModel> {
//        LoginScreenViewModel(
//            saveLoginUserUseCase = get(),
//            getLoginUserUseCase = get()
//        )
//    }
    viewModel { SignInScreenViewModel(get()) }
    viewModel { SignUpScreenViewModel(get()) }
    viewModel { MainScreenViewModel(get()) }
}