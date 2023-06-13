package com.chin.ClassroomApi.Services.UserServices
import com.chin.ClassroomApi.DTO.Request.UpdateUserRequest
import com.chin.ClassroomApi.Entities.ClassroomEntity
import com.chin.ClassroomApi.Entities.UserEntity
import com.chin.ClassroomApi.Repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepo: UserRepository,
) {
    fun findByEmail(id: String): UserEntity? {
        return userRepo.findByEmail(id)
    }
    @Transactional
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
    fun getClassroomWasCreatedById(id: String): List<ClassroomEntity>? {
        val userEntity = userRepo.findById(id)
        if (!userEntity.isPresent) {
            return null
        }
        return  userEntity.get().classroomEntity
    }
    fun updateUser(user: UpdateUserRequest): UserEntity? {
        print(user.id)
        val currentUser = userRepo.findById(user.id).orElse(null)
        currentUser?.apply {
            user.displayName?.let {
                this.displayName = it
            }
            user.photoUrl?.let {
                this.photoUrl = it
            }
            user.birthday?.let {
                this.birthday = it
            }
            return userRepo.save(this)
        }
        return currentUser
    }
}