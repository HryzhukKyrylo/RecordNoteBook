package com.example.data.storage.models

import com.example.domain.models.LoginUserModel

data class LoginUserDTO(
    val login: String,
    val password: String
)

fun LoginUserDTO.mapToModel(): LoginUserModel {
    return LoginUserModel(
        login = this.login,
        password = this.password,
    )
}

fun LoginUserModel.mapToDTO(): LoginUserDTO {
    return LoginUserDTO(
        login = this.login,
        password = this.password,
    )
}