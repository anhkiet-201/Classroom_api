package com.chin.ClassroomApi.Repositories

import com.chin.ClassroomApi.Entities.UserEntity
import jakarta.validation.constraints.Email
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, String>{
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
}