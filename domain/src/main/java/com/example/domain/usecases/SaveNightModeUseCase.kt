package com.example.domain.usecases

import com.example.domain.repository.UserRepository

class SaveNightModeUseCase(private val repository: UserRepository) {
    fun execute(mode: Int) {
        repository.saveNightMode(mode = mode)
    }
}