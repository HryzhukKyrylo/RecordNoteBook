package com.example.data.storage.localstorage

import com.example.data.storage.models.UserLoginDTO
import com.example.data.storage.models.UserNotateDTO
import com.example.domain.Response

interface LocalStorage {
    fun getUserNotates(userLogNameParam: String): List<UserNotateDTO>
    fun getNotate(userLogNameParam: String, timeLastChangeParam: Long): UserNotateDTO
    fun getLoginUser(userLogName: String): UserLoginDTO?

    fun saveLoginUser(userLogin: UserLoginDTO): Response
    fun saveUserNotate(userDTO: UserNotateDTO): Boolean
    fun saveAllUserNotates(userNotates: List<UserNotateDTO>)

    fun removeNotate(userDTO: UserNotateDTO): Boolean
    fun removeUserAllNotates(userLogin: String)
    fun removeUserLogin(userLogin: String)
}