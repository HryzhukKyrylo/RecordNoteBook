package com.example.domain.models

import java.io.Serializable

data class UserNotateModel(
    val userLogName: String,
    val title: String? = null,
    val logData: String? = null,
    val privateInfo: String? = null,
    val timeCreate: Long,
    val timeLastChange: Long,
) : Serializable