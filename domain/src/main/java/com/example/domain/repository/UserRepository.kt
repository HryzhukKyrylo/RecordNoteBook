package com.example.domain.repository

import com.example.domain.models.CreateUserParams
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel

interface UserRepository {
    fun saveLoginUser(loginUserParams: LoginUserParams)
    fun saveUserNotate(userParams: CreateUserParams): Boolean
    fun saveNightMode(mode: Int)
    fun saveAllUserNotates(userNotates: List<UserNotateModel>)
    fun saveNewPassword(loginUserParams: LoginUserParams)

    fun getUserNotates(userParam: String): List<UserNotateModel>
    fun getUserLogin(loginParam: String): LoginUserModel?
    fun getNightMode(): Int

    fun removeNotate(userNotateModel: UserNotateModel): Boolean
    fun removeUserAllNotates(userLogName: String)
    fun removeUserLogin(userLogName: String): Boolean

}