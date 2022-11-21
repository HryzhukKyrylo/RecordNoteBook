package com.example.domain.models

data class LoginUserModel(
    val login: String,
    val password: String,
) {
    companion object {
        fun emptyLoginUserModel(): LoginUserModel {
            return LoginUserModel("", "")
        }
    }
}

fun LoginUserModel.toParam(): LoginUserParams {
    return LoginUserParams(
        loginParam = this.login,
        passwordParam = this.password
    )
}