package com.example.domain.usecases.settingscreen

import com.example.domain.Response
import com.example.domain.repository.UserRepository

class DeleteAccountUseCase(private val repository: UserRepository) {
    fun execute(logName: String): Response {
        val resVal = repository.deleteAccount(logName)
        return resVal
    }
}