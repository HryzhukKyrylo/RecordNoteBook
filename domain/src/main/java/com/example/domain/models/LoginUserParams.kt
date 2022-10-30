package com.example.domain.models

import java.io.Serializable

data class LoginUserParams(
    val loginParam: String,
    val passwordParam: String
) :Serializable