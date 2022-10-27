package com.example.domain.repository

import com.example.domain.models.LoginUserModel
import com.example.domain.models.LoginUserParams

interface UserRepository {
    fun getLoginUser(): LoginUserModel
    fun saveLoginUser(loginUserParams: LoginUserParams): Boolean
}