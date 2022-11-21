package com.example.domain.repository

import com.example.domain.Response
import com.example.domain.models.CreateUserParams
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel

interface UserRepository {
    fun saveLoginUser(loginUserParams: LoginUserParams): Response
    fun saveUserNotate(userParams: CreateUserParams): Boolean
    fun saveNightMode(mode: Int)
    fun saveAllUserNotates(userNotates: List<UserNotateModel>)


    fun getUserNotates(userParam: String): List<UserNotateModel>
    fun getLoginUser(loginParam: String): LoginUserModel?
    fun getNightMode(): Int


    fun removeNotate(userNotateModel: UserNotateModel): Boolean
    fun removeUserAllNotates(userLogName: String): Response
    fun deleteAccount(userName: String): Response
    fun deleteUserLogin(userName: String): Response
}