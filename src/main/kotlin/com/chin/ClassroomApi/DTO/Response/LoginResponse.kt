package com.chin.ClassroomApi.DTO.Response

import com.chin.ClassroomApi.Entities.UserEntity

data class LoginResponse(
    val token: String,
    val user: UserEntity
)
