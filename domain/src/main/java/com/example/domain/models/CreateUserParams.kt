package com.example.domain.models

data class CreateUserParams(
    val logName: String?,
    val title: String?,
    val logData: String,
    val privateInfo: String,
    val createTimestamp: Long,
    val lastTimestamp: Long,
    val isCreated: Boolean,
)