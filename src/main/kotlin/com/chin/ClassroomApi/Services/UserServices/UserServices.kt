package com.chin.ClassroomApi.Services.UserServices

import com.chin.ClassroomApi.Entities.UserEntity
import com.chin.ClassroomApi.Repositories.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepo: UserRepository,
) {
    fun findByEmail(id: String): UserEntity? {
        return userRepo.findByEmail(id)
    }

    fun findById(name: String): UserEntity? {
        var userEntity: UserEntity? = null
        val x = userRepo.findById(name)
        if (x.isPresent) {
            userEntity = x.get()
        }
        return userEntity
    }

    fun existsByEmail(email: String): Boolean {
        return userRepo.existsByEmail(email)
    }

    fun save(user: UserEntity): UserEntity {
        return userRepo.save(user)
    }
}