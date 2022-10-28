package com.example.domain.models

data class UserNotateModel(
    val userLogName: String,
    val title: String? = null,
    val log: String? = null,
    val pass: String? = null,
    val timeCreate: Long,
    val timeLastChange: Long,
)