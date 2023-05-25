package com.chin.ClassroomApi.DTO.Request

data class RegisterRequest (
    val email: String,
    val password: String,
    val userName: String,
    val birthDay: Long
)