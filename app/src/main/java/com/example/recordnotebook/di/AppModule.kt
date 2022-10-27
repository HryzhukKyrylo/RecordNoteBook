package com.example.recordnotebook.di

import com.example.recordnotebook.ui.signinscreen.SignInScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

//    viewModel<LoginScreenViewModel> {
//        LoginScreenViewModel(
//            saveLoginUserUseCase = get(),
//            getLoginUserUseCase = get()
//        )
//    }
    viewModelOf(::SignInScreenViewModel)
}