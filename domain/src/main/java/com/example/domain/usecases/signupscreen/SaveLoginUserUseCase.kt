package com.example.domain.usecases.signupscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository
import com.example.domain.usecases.LogIsExistResult
import com.example.domain.usecases.SavedErrorResult
import com.example.domain.usecases.SavedSuccessResult

class SaveLoginUserUseCase(private val repository: UserRepository) {
    fun execute(loginUserParams: LoginUserParams): Response {
        val resVal = try {
            val logs =
                repository.getUserLogin(loginParam = loginUserParams.loginParam)
            if (logs == null) {
                repository.saveLoginUser(loginUserParams)
                IOResponse.Success(
                    message = SavedSuccessResult,
                    data = null
                )
            } else {
                IOResponse.Error(errorMessage = LogIsExistResult)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(errorMessage = SavedErrorResult)
        }
        return resVal
    }
}