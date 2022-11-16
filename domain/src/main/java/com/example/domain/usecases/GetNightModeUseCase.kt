package com.example.domain.usecases

import com.example.domain.repository.UserRepository

class GetNightModeUseCase(private val repository: UserRepository) {
    fun execute(): Int {
        val resVal = repository.getNightMode()
        return resVal
    }
}