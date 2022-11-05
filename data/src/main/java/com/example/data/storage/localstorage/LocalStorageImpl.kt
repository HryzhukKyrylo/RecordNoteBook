package com.example.data.storage.localstorage

import com.example.data.storage.models.UserLoginDTO
import com.example.data.storage.models.UserNotateDTO

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

    override fun saveLoginUser(userLogin: UserLoginDTO): Boolean {
        val result = try {
            database.userLoginDao().saveUserLogin(userLogin)
            true
        } catch (ex: Exception) {
            false
        }
        return result
    }
}