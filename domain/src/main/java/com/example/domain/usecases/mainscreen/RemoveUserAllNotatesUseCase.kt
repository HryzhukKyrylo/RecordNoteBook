package com.example.domain.usecases.mainscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.MainDeleteSuccessResult
import com.example.domain.usecases.MainSomethingWentWrongResult

class RemoveUserAllNotatesUseCase(private val repository: UserRepository) {
    fun execute(userNotateModel: String): Response {
        val res = try {
            repository.removeUserAllNotates(userNotateModel)
            IOResponse.Success(
                message = MainDeleteSuccessResult,
                data = null
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(
                MainSomethingWentWrongResult
            )
        }
        return res
    }
}