package com.example.data.storage.localstorage

import com.example.data.storage.models.UserLoginDTO
import com.example.data.storage.models.UserNotateDTO
import com.example.domain.IOResponse
import com.example.domain.Response

class LocalStorageImpl(
    private val database: UserDatabase,
) : LocalStorage {

    override fun getUserNotates(userLogNameParam: String): List<UserNotateDTO> {
        return database.userDao().getAllUserNotates(userLogName = userLogNameParam)
    }

    override fun getNotate(userLogNameParam: String, timeLastChangeParam: Long): UserNotateDTO {
        return database.userDao().getUserNotate(userLogNameParam, timeLastChangeParam)
    }

    override fun saveUserNotate(userDTO: UserNotateDTO): Boolean {
        val result = try {
            database.userDao().saveUserNotate(userDTO)
            true
        } catch (ex: Exception) {
            false
        }
        return result
    }

    override fun removeNotate(userDTO: UserNotateDTO): Boolean {
        val result = try {
            database.userDao().delete(userDTO)
            true
        } catch (ex: Exception) {
            false
        }
        return result
    }

    override fun getLoginUser(userLogNameParam: String): UserLoginDTO? {
        return database.userLoginDao()
            .getUserLogin(userLogNameParam = userLogNameParam)
    }

    override fun saveLoginUser(userLogin: UserLoginDTO): Response {
        val result = try {
            val logs = database.userLoginDao().getUserLogin(userLogNameParam = userLogin.login)
            if (logs == null) {
                database.userLoginDao().saveUserLogin(userLogin)
                IOResponse.Succsess(message = "saved - succsess", data = null)
            } else {
                IOResponse.Error(errorMessage = "log is exist. please write another log name")
            }
        } catch (ex: Exception) {
            IOResponse.Error(errorMessage = "saved - error. something went wrong")
        }
        return result
    }
}