package com.example.domain.repository

import com.example.domain.models.CreateUserParams
import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams
import com.example.domain.models.UserNotateModel

interface UserRepository {
    fun getLoginUser(): LoginUserModel
    fun saveLoginUser(loginUserParams: LoginUserParams): Boolean
    fun getUserNotates(userParam: String): List<UserNotateModel>
    fun saveUserNotate(userParams: CreateUserParams): Boolean
    fun removeNotate(userNotateModel: UserNotateModel): Boolean
}