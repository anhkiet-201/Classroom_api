package com.chin.ClassroomApi.DTO.Request

import com.chin.ClassroomApi.Entities.UserEntity

data class UpdateUserRequest(
    val id: String,
    val email: String? = null,
    val displayName: String? = null,
    val photoUrl: String? = null,
    val birthday: Long? = null
)
