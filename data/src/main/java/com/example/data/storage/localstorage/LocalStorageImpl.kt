package com.example.data.storage.localstorage

import android.app.Application
import com.example.data.R
import com.example.data.storage.models.UserLoginDTO
import com.example.data.storage.models.UserNotateDTO
import com.example.domain.IOResponse
import com.example.domain.Response
import com.example.domain.utils.MessageException

class LocalStorageImpl(
    private val context: Application,
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

    override fun saveAllUserNotates(userNotates: List<UserNotateDTO>) {
        database.userDao().saveAllUserNotate(userNotates)
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
                IOResponse.Success(message = context.getString(R.string.saved_success), data = null)
            } else {
                IOResponse.Error(errorMessage = context.getString(R.string.log_is_exist))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            IOResponse.Error(errorMessage = context.getString(R.string.saved_error))
        }
        return resVal
    }

    override fun removeUserAllNotates(userLogin: String) {
        try {
            database.userDao().deleteUserAllNotates(userLogin)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw MessageException(context.getString(R.string.delete_error_wrong))
        }
    }

    override fun removeUserLogin(userLogin: String) {
        try {
            val oldUser = database.userLoginDao().getUserLogin(userLogin)
                ?: throw MessageException(context.getString(R.string.user_doesnt_exist))
            database.userLoginDao().delete(oldUser)
        } catch (ex: Exception) {
            ex.printStackTrace()
            throw Exception(context.getString(R.string.delete_user_wrong))
        }
    }

}