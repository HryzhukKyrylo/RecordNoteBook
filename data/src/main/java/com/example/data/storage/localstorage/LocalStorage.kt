package com.example.data.storage.localstorage

import com.example.data.storage.models.UserLoginDTO
import com.example.data.storage.models.UserNotateDTO

interface LocalStorage {
    fun getUserNotates(userLogNameParam: String): List<UserNotateDTO>
    fun getNotate(userLogNameParam: String, timeLastChangeParam: Long): UserNotateDTO
    fun saveUserNotate(userDTO: UserNotateDTO): Boolean
    fun removeNotate(userDTO: UserNotateDTO): Boolean
    fun getLoginUser(userLogNameParam: String): UserLoginDTO?
    fun saveLoginUser(userLogin: UserLoginDTO): Boolean
}