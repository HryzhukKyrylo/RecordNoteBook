package com.example.domain.usecases.accountscreen

import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.repository.UserRepository

class SaveNewPasswordUseCase(
    private val repository: UserRepository,
) {
    fun execute(
        login: String,
        curPass: String,
        newPass: String,
        confPass: String
    ): Response {
        //todo implement
        val res = try {
            val login = repository.getLoginUser(login)
            val password = login?.password ?: return IOResponse.Error("Can't find old password")
            val loginName = login?.login ?: return IOResponse.Error("Can't find old login")
            verifyEmptyPassword(password, curPass, newPass, confPass)
            verifyPassword(password, curPass)
            sheckTheSamePassword(curPass,newPass)
            confirmPassword(newPass, confPass)
            if (password == curPass && newPass == confPass) {
                val param = LoginUserParams(loginName, newPass)
                repository.saveNewPassword(param)
            }

            IOResponse.Success("Saved - success",null)
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(ex.message)
        } catch (myEx: IllegalStateException) {
            myEx.printStackTrace()
            IOResponse.Error(myEx.message)
        }
        return res
    }

    private fun sheckTheSamePassword(curPass: String, newPass: String) {
        if(curPass == newPass){
            throw IllegalStateException("The new password matches the current one")
        }
    }

    private fun confirmPassword(newPass: String, confPass: String) {
        if (newPass != confPass) {
            throw IllegalStateException("Confirm password wrong")
        }
    }

    private fun verifyPassword(password: String, curPass: String) {
        if (password != curPass) {
            throw IllegalStateException("Current password is wrong")
        }
    }

    private fun verifyEmptyPassword(
        password: String,
        curPass: String,
        newPass: String,
        confPass: String
    ) {
        if (curPass.trim().isEmpty()) {
            throw IllegalStateException("Current pass is empty - please write current password")
        }
        if (newPass.trim().isEmpty()) {
            throw IllegalStateException("Empty field - please write password")
        }
        if (confPass.trim().isEmpty()) {
            throw IllegalStateException("Empty field - please write password")
        }
    }
}