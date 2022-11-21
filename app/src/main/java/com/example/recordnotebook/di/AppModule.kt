package com.example.recordnotebook.di

import com.example.recordnotebook.ui.accountscreen.AccountScreenViewModel
import com.example.recordnotebook.ui.createscreen.CreateScreenViewModel
import com.example.recordnotebook.ui.detailscreen.DetailScreenViewModel
import com.example.recordnotebook.ui.mainscreen.MainScreenViewModel
import com.example.recordnotebook.ui.settingscreen.SettingScreenViewModel
import com.example.recordnotebook.ui.signinscreen.SignInScreenViewModel
import com.example.recordnotebook.ui.signupscreen.SignUpScreenViewModel
import com.example.recordnotebook.ui.splashscreen.SplashScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SignInScreenViewModel(context = get(), verifyLoginUserCase = get()) }
    viewModel { SignUpScreenViewModel(context = get(), saveLoginUserUseCase = get()) }
    viewModel {
        MainScreenViewModel(
            getUserNotatesUseCase = get(),
            removeUserNotateUseCase = get(),
            removeUserAllNotatesUseCase = get(),
            getNightModeUseCase = get(),
            saveNightModeUseCase = get()
        )
    }
    viewModel { CreateScreenViewModel(saveUserNotateUseCase = get()) }
    viewModel { DetailScreenViewModel() }
    viewModel { SplashScreenViewModel(getNightModeUseCase = get()) }
    viewModel { SettingScreenViewModel(deleteAccount = get()) }
    viewModel { AccountScreenViewModel( get(),get()) }
}