package com.example.recordnotebook.di

import com.example.data.sessionapp.SessionApp
import com.example.data.sessionapp.SessionAppMutable
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
    single<SessionApp> {
        val sessionapp: SessionApp = get<SessionAppMutable>()
        sessionapp
    }

    single<SessionAppMutable> {
        SessionAppMutable()
    }

    viewModel {
        SignInScreenViewModel(
            parseResult = get(),
            verifyLoginUserCase = get(),
            sessionApp = get()
        )
    }
    viewModel {
        SignUpScreenViewModel(
            parseResult = get(), saveLoginUserUseCase = get()
        )
    }
    viewModel {
        MainScreenViewModel(
            getUserNotatesUseCase = get(),
            removeUserNotateUseCase = get(),
            removeUserAllNotatesUseCase = get(),
            getNightModeUseCase = get(),
            saveNightModeUseCase = get(),
            sessionApp = get(),
            parseResult = get(),
        )
    }
    viewModel {
        CreateScreenViewModel(
            saveUserNotateUseCase = get(),
            sessionApp = get()
        )
    }
    viewModel { DetailScreenViewModel() }
    viewModel {
        SplashScreenViewModel(
            getNightModeUseCase = get()
        )
    }
    viewModel {
        SettingScreenViewModel(
            deleteAccount = get(),
            sessionApp = get(),
            parseResult = get()
        )
    }
    viewModel {
        AccountScreenViewModel(
            getUserLoginUseCase = get(),
            saveNewLoginUseCase = get(),
            sessionApp = get(),
            saveNewPasswordUseCase = get(),
            parseResult = get()
        )
    }
}