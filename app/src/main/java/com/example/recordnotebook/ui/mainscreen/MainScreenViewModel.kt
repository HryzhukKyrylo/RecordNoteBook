package com.example.recordnotebook.ui.mainscreen

import androidx.lifecycle.ViewModel
import com.example.domain.usecases.mainscreen.GetUserNotatesUseCase

class MainScreenViewModel(
    private val getUserNotatesUseCase: GetUserNotatesUseCase
) : ViewModel() {

}