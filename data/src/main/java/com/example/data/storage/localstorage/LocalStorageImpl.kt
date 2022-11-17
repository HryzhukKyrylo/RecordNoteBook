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

    override fun getLoginUser(userLogName: String): UserLoginDTO? {
        return database.userLoginDao()
            .getUserLogin(userLogNameParam = userLogName)
    }

    override fun saveLoginUser(userLogin: UserLoginDTO): Response {
        val resVal = try {
            val logs = database.userLoginDao().getUserLogin(userLogNameParam = userLogin.login)
            if (logs == null) {
                database.userLoginDao().saveUserLogin(userLogin)
                IOResponse.Success(message = "saved - succsess", data = null)
            } else {
                IOResponse.Error(errorMessage = "log is exist. please write another log name")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(errorMessage = "saved - error. something went wrong")
        }
        return resVal
    }

    override fun removeUserAllNotates(userLogin: String) {
        try {
            database.userDao().deleteUserAllNotates(userLogin)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("delete - error. something went wrong")
        }
    }

    override fun removeUserLogin(userLogin: String) {
        try {
            val oldUser = database.userLoginDao().getUserLogin(userLogin)
                ?: throw Exception("User doesn't exist")
            database.userLoginDao().delete(oldUser)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception("Deleted user login - something went wrong")
        }
    }
}